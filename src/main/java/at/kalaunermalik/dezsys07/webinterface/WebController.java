package at.kalaunermalik.dezsys07.webinterface;


import at.kalaunermalik.dezsys07.db.Entry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {
    private static final String POST_URL = "http://localhost:8080/entries";
    private static final String GET_ENTRY = "http://localhost:8080/entries/{id}";
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
        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updatePost(@RequestParam(value = "entryid", required = false, defaultValue = "") String id, @RequestParam(value = "title", required = false, defaultValue = "") String title, Model model) {
        Map<String, String> params = new HashMap<>();
        model.addAttribute("entry", new Entry());
        if (!id.isEmpty()) {
            return "redirect:updateSubmit?id=" + id;
        } else if (!title.isEmpty()) {
            params.put("title", title);
            Entry[] entries = restTemplate.getForObject(GET_BY_TITLE, Entry[].class, params);
            if (entries.length > 0) {
                return "redirect:updateSubmit?id=" + entries[0].getId();
            }
        }
        model.addAttribute("resultmessage", "Es existiert kein Eintrag mit diesen Angaben!");
        return "result_special";
    }

    @RequestMapping(value = "/updateSubmit", method = RequestMethod.GET)
    public String updateSubmitGet(@RequestParam(value = "id", required = true) String id, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        Entry e = restTemplate.getForObject(GET_ENTRY, Entry.class, params);
        if (e != null) {
            model.addAttribute("entry", e);
            return "updateSubmit";
        }
        model.addAttribute("resultmessage", "Es existiert kein Eintrag mit diesen Angaben!");
        return "result_special";
    }


    @RequestMapping(value = "/updateSubmit", method = RequestMethod.POST)
    public String updateSubmitPost(@ModelAttribute Entry entry, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(entry.getId()));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(GET_ENTRY, entry, params);
        model.addAttribute("resultmessage", "Eintrag erfolgreich aktualisiert!");
        model.addAttribute("entry", entry);
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
            restTemplate.delete(GET_ENTRY, params);

            model.addAttribute("resultmessage", "Eintrag mit id " + id + " erfolgreich entfernt!");
        } else if (!title.isEmpty()) {
            params.put("title", title);
            Entry[] entries = restTemplate.getForObject(GET_BY_TITLE, Entry[].class, params);
            StringBuilder sb = new StringBuilder();
            for (Entry cur : entries) {
                params.clear();
                params.put("id", String.valueOf(cur.getId()));
                restTemplate.delete(GET_ENTRY, params);
                sb.append(cur.getId()).append(", ");
            }

            if (entries.length == 0) {
                model.addAttribute("resultmessage", "Keine Eintraege entfernt!");
            } else if (entries.length == 1) {
                model.addAttribute("resultmessage", "Eintrag mit id " + entries[0].getId() + " erfolgreich entfernt!");
            } else {
                model.addAttribute("resultmessage", "Eintraege mit ids " + sb.toString() + " erfolgreich entfernt!");
            }
        } else {
            model.addAttribute("resultmessage", "Weder ID noch Titel angegeben!");
        }
        return "result_special";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public String httpClientErrorException(Model model) {
        model.addAttribute("errormessage", "HttpClientErrorException. Eventuell sind keine Elemente mit dem angegebenen Merkmal vorhanden.");
        return "error_message";
    }
}