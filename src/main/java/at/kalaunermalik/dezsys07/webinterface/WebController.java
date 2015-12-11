package at.kalaunermalik.dezsys07.webinterface;


import at.kalaunermalik.dezsys07.db.Entry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    private static final String POST_URL = "http://localhost:8080/entries";
    private static final String DELETE_URL = "http://localhost:8080/entries/{id}";
    private static final String GET_BY_TITLE = "http://localhost:8080/entries/title={title}";
    private final RestTemplate restTemplate;


    public WebController() {
        this.restTemplate = new RestTemplate();
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createGet(Model model) {
        model.addAttribute("entry", new Entry());
        return "create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPost(@ModelAttribute Entry entry, Model model) {
        Entry result = restTemplate.postForObject(POST_URL, entry, Entry.class);
        model.addAttribute("entry", result);
        model.addAttribute("resultmessage", "Eintrag erfolgreich erstellt!");
        return "result";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateGet() {
        return "index";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updatePost(Model model) {
    /*
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "2");

        EmployeeVO updatedEmployee = new EmployeeVO(2, "New Name", "Gilly", "test@email.com");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, updatedEmployee, params);
        */
        return "result";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(Model model) {
        model.addAttribute("entry", new Entry());
        return "delete";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value = "entryid", required = false, defaultValue = "") String id, @RequestParam(value = "title", required = false, defaultValue = "") String title, Model model) {
        Map<String, String> params = new HashMap<>();
        if (!id.isEmpty()) {
            params.put("id", id);
            restTemplate.delete(DELETE_URL, params);

            model.addAttribute("resultmessage", "Eintrag mit id " + id + " erfolgreich entfernt!");
        } else if (!title.isEmpty()) {
            params.put("title", title);
            Entry[] entries = restTemplate.getForObject(GET_BY_TITLE, Entry[].class, params);
            StringBuilder sb = new StringBuilder();
            for (Entry cur : entries) {
                params.clear();
                params.put("id", String.valueOf(cur.getId()));
                restTemplate.delete(DELETE_URL, params);
                sb.append(cur.getId()).append(", ");
            }

            if (entries.length == 0) {
                model.addAttribute("resultmessage", "Keine Eintraege entfernt!");
            } else if (entries.length == 1) {
                model.addAttribute("resultmessage", "Eintrag mit id " + entries[0] + " erfolgreich entfernt!");
            } else {
                model.addAttribute("resultmessage", "Eintraege mit ids " + sb.toString() + " erfolgreich entfernt!");
            }
        } else {
            model.addAttribute("resultmessage", "Weder ID noch Titel angegeben!");
        }
        return "result_delete";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public String httpClientErrorException(Model model) {
        model.addAttribute("errormessage", "HttpClientErrorException. Eventuell sind keine Elemente mit dem angegebenen Merkmal vorhanden.");
        return "error_message";
    }
}