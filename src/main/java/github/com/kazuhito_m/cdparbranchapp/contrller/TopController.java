package github.com.kazuhito_m.cdparbranchapp.contrller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopController {
    @Value("${server.contextPath}")
    private String serverContextPath;

    @ModelAttribute("cp")
    String cp() {
        return serverContextPath;
    }

    @RequestMapping({"/", "/index"})
    String show() {
        return "index";
    }
}
