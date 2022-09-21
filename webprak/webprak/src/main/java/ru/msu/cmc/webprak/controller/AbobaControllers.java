package ru.msu.cmc.webprak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.msu.cmc.webprak.DAO.*;
import ru.msu.cmc.webprak.DAO.impl.*;
import ru.msu.cmc.webprak.models.*;

import java.lang.Iterable;
import java.util.List;

@Controller
@SessionAttributes({"chaptersAttr",
                    "login",
                    "password",
                    "chapterThemes",
                    "themeAttrMessages",
                    "themeAttrFiles",
                    "themeId",
                    "messageId",
                    "messageFiles",
                    "statistic"
})
public class AbobaControllers {

    @Autowired
    private UserDAO userDAO = new UserDAOImpl();

    @Autowired
    private ChapterDAO chapterDAO = new ChapterDAOImpl();

    @Autowired
    private ThemeDAO themeDAO = new ThemeDAOImpl();

    @Autowired
    private MessageDAO messageDAO = new MessageDAOImpl();

    @Autowired
    private FileDAO fileDAO = new FileDAOImpl();

    @Autowired
    private MessageFileDAO messageFileDAO = new MessageFileDAOImpl();

    @RequestMapping(value = "/")
    public String index(Model model) {
        return "redirect:/home";
    }

    @RequestMapping(value = "/home")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping(value = "/register")
    public String register(Model model) {
        return "register";
    }

    @RequestMapping(value = "/chapters")
    public String chapters(Model model) {

        if (!model.containsAttribute("login") ||
            !model.containsAttribute("password") ||
            !userDAO.auth(
                    model.getAttribute("login").toString(),
                    model.getAttribute("password").toString()
            )
        )
            return "redirect:/";
        model.addAttribute("chaptersAttr", chapterDAO.getAll());
        return "chapters";
    }

    @PostMapping(value = "/chapters")
    public String chaptersPost(@RequestParam String name,
                               @RequestParam Long date,
                               @RequestParam String deleteOrCreate,
                               WebRequest request,
                               Model model) {

        request.removeAttribute("chaptersAttr", request.SCOPE_SESSION);
        if (userDAO.getByLogin(
                model.getAttribute("login").toString()
        ).getRights() == 0)
            if (deleteOrCreate.equals("create")) {
                if (chapterDAO.getByName(name) == null)
                    chapterDAO.addChapter(name, date);
            }
            else if (deleteOrCreate.equals("delete")) {
                if (chapterDAO.getByName(name) != null)
                    chapterDAO.delete(chapterDAO.getByName(name));
            }
        return "redirect:/chapters";
    }

    @RequestMapping(value = "/chapters/{name_chapter}")
    public String chapterDetail(@PathVariable(value = "name_chapter") String name_chapter,
                                WebRequest request,
                                Model model) {

        if (chapterDAO.getByName(name_chapter) == null)
            return "redirect:/chapters";
        request.removeAttribute("chaptersAttr", request.SCOPE_SESSION);
        model.addAttribute("chaptersAttr", name_chapter);
        if (model.containsAttribute("chapterThemes"))
            request.removeAttribute("chapterThemes", request.SCOPE_SESSION);
        model.addAttribute(
                "chapterThemes",
                chapterDAO.getThemes(
                        chapterDAO.getByName(name_chapter)
                )
        );
        return "chapterDetail";
    }

    @PostMapping(value = "/chapters/{name_chapter}")
    public String chapterDetailPost(@PathVariable(value = "name_chapter") String name_chapter,
                                    @RequestParam String name,
                                    @RequestParam Long date,
                                    @RequestParam String deleteOrCreate,
                                    Model model) {
        if (userDAO.getByLogin(model.getAttribute("login").toString()).getRights() != 0)
            return "redirect:/chapters/" + name_chapter;
        if (deleteOrCreate.equals("create")) {
            themeDAO.save(new Theme(0L,
                                    chapterDAO.getByName(name_chapter),
                                    name,
                                    date
                                   )
            );
        }
        else if (deleteOrCreate.equals("delete")) {
            List<Theme> lt = themeDAO.getListByChapter(name_chapter);
            for (int i = 0; i < lt.size(); ++i)
                if (lt.get(i).getName().equals(name)) {
                    themeDAO.deleteById(lt.get(i).getId());
                    break;
                }
        }
        return "redirect:/chapters/" + name_chapter;
    }

    @PostMapping(value = "/register")
    public String registerPost(@RequestParam String login,
                               @RequestParam String password,
                               @RequestParam String rights,
                               @RequestParam Long date,
                               Model model) {
        if (userDAO.register(login, password, rights, date))
            return "redirect:/";
        else
            return "register";
    }

    @PostMapping(value = "/home")
    public String homePost(@RequestParam String login,
                           @RequestParam String password,
                           WebRequest request,
                           Model model) {
        if (model.containsAttribute("login"))
            request.removeAttribute("login", request.SCOPE_SESSION);
        if (model.containsAttribute("password"))
            request.removeAttribute("password", request.SCOPE_SESSION);
        if (userDAO.auth(login, password)) {
            model.addAttribute("login", login);
            model.addAttribute("password", password);
            return "redirect:/chapters";
        }
        else
            return "redirect:/";
    }

    @RequestMapping(value = "/chapters/{name_chapter}/{id_theme}")
    public String theme(@PathVariable(value = "name_chapter") String name_chapter,
                        @PathVariable(value = "id_theme") Long id_theme,
                        WebRequest request,
                        Model model) {
        if (!model.containsAttribute("login") ||
            !model.containsAttribute("password") ||
            !userDAO.auth(model.getAttribute("login").toString(),
                         model.getAttribute("password").toString())
        )
            return "redirect:/";

        if (model.containsAttribute("themeAttrMessages"))
            request.removeAttribute("themeAttrMessages", request.SCOPE_SESSION);
        model.addAttribute("themeAttrMessages",
                                       themeDAO.getMessagesByTheme(themeDAO.getById(id_theme))
        );
        if (model.containsAttribute("themeAttrFiles"))
            request.removeAttribute("themeAttrFiles", request.SCOPE_SESSION);
        model.addAttribute("themeAttrFiles",
                                       themeDAO.getFilesByTheme(themeDAO.getById(id_theme))
        );
        if (model.containsAttribute("themeId"))
            request.removeAttribute("themeId", request.SCOPE_SESSION);
        model.addAttribute("themeId", id_theme);
        if (model.containsAttribute("chaptersAttr"))
            request.removeAttribute("chaptersAttr", request.SCOPE_SESSION);
        model.addAttribute("chaptersAttr", name_chapter);
        return "theme";
    }

    @PostMapping(value = "/chapters/{name_chapter}/{id_theme}/delmessage/{id_message}")
    public String messageDelete(@PathVariable(value = "name_chapter") String name_chapter,
                                @PathVariable(value = "id_theme") Long id_theme,
                                @PathVariable(value = "id_message") Long id_message,
                                WebRequest request,
                                Model model) {
        if (!model.containsAttribute("login") ||
            !model.containsAttribute("password") ||
            userDAO.getByLogin(model.getAttribute("login").toString()).getRights() == 2
        )
            return "redirect:/";
        if (!messageDAO.getById(id_message)
                       .getUser()
                       .getLogin()
                       .equals(model.getAttribute("login").toString()) &&
            userDAO.getByLogin(model.getAttribute("login").toString()).getRights() != 0
        )
            return "redirect:/chapters/{name_chapter}/{id_theme}";
        messageDAO.deleteById(id_message);
        return "redirect:/chapters/{name_chapter}/{id_theme}";
    }

    @PostMapping(value = "/chapters/{name_chapter}/{id_theme}/addmessage")
    public String messageAdd(@PathVariable(value = "name_chapter") String name_chapter,
                             @PathVariable(value = "id_theme") Long id_theme,
                             @RequestParam String content,
                             @RequestParam Long date,
                             WebRequest request,
                             Model model) {
        if (!model.containsAttribute("login") ||
            !model.containsAttribute("password") ||
            userDAO.getByLogin(model.getAttribute("login").toString()).getRights() == 2
        )
            return "redirect:/";
        messageDAO.addMessage(chapterDAO.getByName(name_chapter),
                              themeDAO.getById(id_theme),
                              userDAO.getByLogin(model.getAttribute("login").toString()),
                              null,
                              content,
                              date
        );
        return "redirect:/chapters/{name_chapter}/{id_theme}";
    }

    @RequestMapping(value = "chapters/{name_chapter}/{id_theme}/{id_message}")
    public String message(
            @PathVariable(value = "name_chapter") String name_chapter,
            @PathVariable(value = "id_theme") Long id_theme,
            @PathVariable(value = "id_message") Long id_message,
            WebRequest request,
            Model model
    ) {
        if (!model.containsAttribute("login") ||
                !model.containsAttribute("password") ||
                userDAO.getByLogin(model.getAttribute("login").toString()).getRights() == 2
        )
            return "redirect:/";
        if (model.containsAttribute("messageFiles"))
            request.removeAttribute("messageFiles", request.SCOPE_SESSION);
        if (model.containsAttribute("messageId"))
            request.removeAttribute("messageId", request.SCOPE_SESSION);
        model.addAttribute("messageId", id_message);
        model.addAttribute("messageFiles", messageDAO.getFiles(messageDAO.getById(id_message)));
        return "message";
    }

    @PostMapping(value = "/chapters/{name_chapter}/{id_theme}/statistic")
    public String statisticPost(
            @PathVariable(value = "name_chapter") String name_chapter,
            @PathVariable(value = "id_theme") Long id_theme,
            @RequestParam Long from,
            @RequestParam Long to,
            WebRequest request,
            Model model
    ) {
        if (!model.containsAttribute("login") ||
                !model.containsAttribute("password") ||
                userDAO.getByLogin(model.getAttribute("login").toString()).getRights() == 2
        )
            return "redirect:/";
        if (model.containsAttribute("statistic"))
            request.removeAttribute("statistic", request.SCOPE_SESSION);
        model.addAttribute("statistic",
                themeDAO.getUsersActivity(themeDAO.getById(id_theme),
                        from,
                        to
                )
        );
        return "statistic";
    }

    @PostMapping(value = "/chapters/{name_chapter}/{id_theme}/{id_message}/add")
    public String fileAdd(
            @PathVariable(value = "name_chapter") String name_chapter,
            @PathVariable(value = "id_theme") Long id_theme,
            @PathVariable(value = "id_message") Long id_message,
            @RequestParam String name,
            @RequestParam String content,
            WebRequest request,
            Model model
    ) {
        if (!model.containsAttribute("login") ||
                !model.containsAttribute("password") ||
                userDAO.getByLogin(model.getAttribute("login").toString()).getRights() == 2
        )
            return "redirect:/";
        fileDAO.addFile(name, content);
        messageFileDAO.addRelation(
                messageDAO.getById(id_message),
                fileDAO.getById(fileDAO.getMaxId())
        );
        return "redirect:/chapters/{name_chapter}/{id_theme}/{id_message}";
    }

    @PostMapping(value = "/chapters/{name_chapter}/{id_theme}/{id_message}/delete/{id_file}")
    public String fileDel(
            @PathVariable(value = "name_chapter") String name_chapter,
            @PathVariable(value = "id_theme") Long id_theme,
            @PathVariable(value = "id_message") Long id_message,
            @PathVariable(value = "id_file") Long id_file,
            WebRequest request,
            Model model
    ) {
        if (!model.containsAttribute("login") ||
                !model.containsAttribute("password") ||
                userDAO.getByLogin(model.getAttribute("login").toString()).getRights() == 2
        )
            return "redirect:/";
        fileDAO.deleteById(id_file);
        return "redirect:/chapters/{name_chapter}/{id_theme}/{id_message}";
    }
}