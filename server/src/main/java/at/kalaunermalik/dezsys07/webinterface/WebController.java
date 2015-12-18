package at.kalaunermalik.dezsys07.webinterface;


import at.kalaunermalik.dezsys07.db.Entry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the HTTP-Requests to the Webapplication.
 *
 * @author Paul Kalauner 5BHIT
 * @version 20151218.1
 */
@Controller
public class WebController {
    private static final String POST_URL = "http://localhost:8080/entries";
    private static final String GET_ENTRY = "http://localhost:8080/entries/{id}";
    private static final String GET_BY_TITLE = "http://localhost:8080/entries/title={title}";
    private static final String SEARCH = "http://localhost:8080/entries?searchstring={searchstring}";
    private final RestTemplate restTemplate;


    /**
     * Default-Constructor
     */
    public WebController() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Mapping for index page
     *
     * @return template name
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Mapping for create page
     *
     * @param model model
     * @return template name
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createGet(Model model) {
        model.addAttribute("entry", new Entry());
        return "create";
    }

    /**
     * Mapping for create submit
     *
     * @param entry the entry which should be created
     * @param model model
     * @return template name
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPost(@ModelAttribute Entry entry, Model model) {
        Entry result = restTemplate.postForObject(POST_URL, entry, Entry.class);
        model.addAttribute("entry", result);
        model.addAttribute("resultmessage", "Eintrag erfolgreich erstellt!");
        return "result";
    }

    /**
     * Mapping for search page
     *
     * @return template name
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchGet() {
        return "search";
    }

    /**
     * Mapping for search submit
     *
     * @param searchstring searchstring
     * @param model model
     * @return template name
     */
    @RequestMapping(value = "/searchsubmit", method = RequestMethod.GET)
    public String searchPost(@RequestParam String searchstring, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("searchstring", searchstring);
        Entry[] entries = restTemplate.getForObject(SEARCH, Entry[].class, params);
        if (entries.length == 0)
            entries = null;
        model.addAttribute("entries", entries);
        return "search_result";
    }

    /**
     * Mapping for update page
     *
     * @return template name
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateGet() {
        return "update";
    }

    /**
     * Mapping for update submit
     *
     * @param id id of the entry or
     * @param title title of the entry
     * @param model model
     * @return template name
     */
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

    /**
     * Mapping for update submit (GET)
     *
     * @param id the id of the entry
     * @param model model
     * @return template name
     */
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


    /**
     * Mapping for update submit (POST)
     *
     * @param entry the entry which should be updated
     * @param model model
     * @return template name
     */
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

    /**
     * Mapping for delete page
     *
     * @param model model
     * @return template name
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(Model model) {
        model.addAttribute("entry", new Entry());
        return "delete";
    }

    /**
     * Mapping for delete submit
     * @param id id of the entry or
     * @param title title of the entry
     * @param model model
     * @return template name
     */
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

    /**
     * ErrorHandler for HttpClientErrorException
     *
     * @param model model
     * @return template name
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public String httpClientErrorException(Model model) {
        model.addAttribute("errormessage", "HttpClientErrorException. Eventuell sind keine Elemente mit dem angegebenen Merkmal vorhanden.");
        return "error_message";
    }
}