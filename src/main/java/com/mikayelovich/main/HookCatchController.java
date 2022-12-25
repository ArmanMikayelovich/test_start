package com.mikayelovich.main;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class HookCatchController {
    @RequestMapping("catch")
    private void catchRequest(HttpServletRequest request) {
        String s = mapRequestToString(request);
        System.out.println("PRINTING REQUEST");
        System.out.println("++++++++++++++++++++=================++++++++++++++++++++");
        System.out.println(s);
        System.out.println("REQUEST PRINTED");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
    private String mapRequestToString(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();

        sb.append("Request Method = [" + request.getMethod() + "], ");
        sb.append("\n");
        sb.append("Request URL Path = [" + request.getRequestURL() + "], ");
        sb.append("\n");
        String headers =
                Collections.list(request.getHeaderNames()).stream()
                        .map(headerName -> headerName + " : " + Collections.list(request.getHeaders(headerName)) + "\n")
                        .collect(Collectors.joining(", "));

        if (headers.isEmpty()) {
            sb.append("Request headers: NONE,");
        }
        else {
            sb.append("Request headers: [" + headers + "],");
        }
        sb.append("\n");

        String parameters =
                Collections.list(request.getParameterNames()).stream()
                        .map(p -> p + " : " + Arrays.asList(request.getParameterValues(p)))
                        .collect(Collectors.joining(", "));

        if (parameters.isEmpty()) {
            sb.append("Request parameters: NONE.");
        }
        else {
            sb.append("Request parameters: [" + parameters + "].");
        }
        sb.append("\n");

        return sb.toString();
    }
}
