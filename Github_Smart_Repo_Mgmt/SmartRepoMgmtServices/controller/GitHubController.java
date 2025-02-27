package com.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private static final List<String> GITHUB_REPOS = List.of(
            "https://api.github.com/repos/AditiSandbhor/JavaRepository",
            "https://api.github.com/repos/Bhushan1828/JavaRepo",
            "https://api.github.com/repos/Athruu07/JavaProgram_C9550-",
            "https://api.github.com/repos/Nickpatil45/APJFSA_N",
            "https://api.github.com/repos/shubhangi-Pawar-bit/APJFSA_N_ANP-C9550",
            "https://api.github.com/repos/pjyadav321/APJFSA_N",
            "https://api.github.com/repos/amanbisen45/APJFSA_N",
            "https://api.github.com/repos/BindiyaShetty18/Anudip-F__Java-Programs",
         //   "https://api.github.com/repos/RutikDanavale",
          //  "https://api.github.com/repos/Sanketchivhe",
            "https://api.github.com/repos/Abhi2971/APJFSA_N",
          //  "https://api.github.com/repos/VaishuDeshmukh-2003",
            "https://api.github.com/repos/Lubnakazi27/AJPFSA",
            "https://api.github.com/repos/sakshikhopade/APJFSA_N",
            "https://api.github.com/repos/prachi-salunkhe2003/APJFSA_N",
            "https://api.github.com/repos/SB2157/APJFSA_N",
          //  "https://api.github.com/repos/ANKITAMORE06/APJFSA_N",
            "https://api.github.com/repos/Aditizanje/APJFSA_N",
            "https://api.github.com/repos/v29m01/AJPF",
            "https://api.github.com/repos/dipakkondhalkar/APJFSA_N",
            "https://api.github.com/repos/omkar17k/omkar17k",
            "https://api.github.com/repos/mangeshkolapkar1144/APJFSA",
            "https://api.github.com/repos/nikitakarape/APJFSA_N",
            "https://api.github.com/repos/PrabhatASharma/APJFSA_N",
            "https://api.github.com/repos/DhananjayGhate111/ANP-CORE_JAVA",
            "https://api.github.com/repos/bhuvanbhoge/APJFSA_N",
            "https://api.github.com/repos/Jarvis1401/Anudip_java_Classes",
            "https://api.github.com/repos/Omkar3321/Java-Course-",
            "https://api.github.com/repos/aniruddhaa7/APJFSA-JavaProgram",
            "https://api.github.com/repos/sanskruti13/APJFSA_JAVA",
            "https://api.github.com/repos/Surajone8/Anudeep_Java",
            "https://api.github.com/repos/PranavTamboli/APJFSA_N",
            "https://api.github.com/repos/shruti-chaudharii/APJFSA_JAVA_COURSE",
            "https://api.github.com/repos/shreyashpawar8910/APJFSA_N",
            "https://api.github.com/repos/aachalakre001/APJFSA_N",
            "https://api.github.com/repos/Pravin224/ANP_java_classes",
            "https://api.github.com/repos/kaivalyakulkarni123/APJSFA_M",
            "https://api.github.com/repos/Bhushan2211/Java_programming",
            //"https://api.github.com/repos/vishrutirane",
           // "https://api.github.com/repos/shriyawankhede",
            "https://api.github.com/repos/vaibhav-11-hub/Java-programs",
            "https://api.github.com/repos/Aarya-Shinde/Lab-Work-",
            "https://api.github.com/repos/SiddhantM007/Java-Lab-1",
            "https://api.github.com/repos/ankushmishra1/APJFSA_N_",
           // "https://api.github.com/repos/Madhurishinde20",
            "https://api.github.com/repos/Snehaupparbawde/APJFSA_N"
    );

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/repos")
    public ResponseEntity<List<Map<String, Object>>> getGitHubRepos() {
        List<Map<String, Object>> repositoryDetails = new ArrayList<>();

        for (String url : GITHUB_REPOS) {
            try {
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);
                if (response != null) {
                    repositoryDetails.add(Map.of(
                            "name", response.get("name"),
                            "owner", ((Map<String, Object>) response.get("owner")).get("login"),
                            "url", response.get("html_url")
                    ));
                }
            } catch (Exception e) {
                repositoryDetails.add(Map.of(
                        "url", url,
                        "error", e.getMessage()
                ));
                System.err.println("Error fetching data for URL: " + url + " - " + e.getMessage());
            }
        }

        return ResponseEntity.ok(repositoryDetails);
    }
}
