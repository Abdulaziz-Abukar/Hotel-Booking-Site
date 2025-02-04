package edu.wgu.d387_sample_code.rest;

import edu.wgu.d387_sample_code.D387SampleCodeApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin()
@RestController
public class WelcomeController {

    @GetMapping("/messages")
    public String getMessages() {
        List<String> messageList = D387SampleCodeApplication.getMessages();
        return String.join(" ", messageList);

    }

    @RequestMapping(path = "/presentation", method = RequestMethod.GET, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public String getPresentation() {
        List<String> times = D387SampleCodeApplication.getPresentationTimes();
        String presAnnouncement = "Join us for a live presentation at: ";
        String timeString = presAnnouncement + "\n" + times.get(0) + "EST\n" + times.get(1) + "MST\n" + times.get(2) + "UTC";
        return timeString;
    }
}
