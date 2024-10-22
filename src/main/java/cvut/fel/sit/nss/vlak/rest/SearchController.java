package cvut.fel.sit.nss.vlak.rest;

import cvut.fel.sit.nss.vlak.model.Connection;
import cvut.fel.sit.nss.vlak.model.Form;
import cvut.fel.sit.nss.vlak.model.User;
import cvut.fel.sit.nss.vlak.model.util.ConnectionDetailsDTO;
import cvut.fel.sit.nss.vlak.security.model.UserDetails;
import cvut.fel.sit.nss.vlak.service.DataSetup;
import cvut.fel.sit.nss.vlak.util.Pair;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cvut.fel.sit.nss.vlak.service.SearchService;

import java.util.List;

@RestController
@RequestMapping("/rest/search")
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    private final SearchService searchService;
    private final DataSetup dataSetup;

    @Autowired
    public SearchController(SearchService searchService, DataSetup dataSetup) {
        this.searchService = searchService;
        this.dataSetup = dataSetup;
    }

    @PostMapping(value = "/connections", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ConnectionDetailsDTO> searchConnections(@RequestBody Form form, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();
        if(!(authentication instanceof AnonymousAuthenticationToken) & authentication != null){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            session.setAttribute("user",user);
        }
        session.setAttribute("originStation",form.getOriginStation());
        session.setAttribute("destinationStation", form.getDestinationStation());
        return searchService.searchConnections(new Pair<>(session,form));
    }

    @GetMapping(value = "/data")
    public void data() {
        dataSetup.dataSetup();
    }
}
