package com.mikayelovich.main;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class HookCatchController {

    private static List<String> list = new ArrayList<>();

    @RequestMapping(value = "catch", produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.GET, RequestMethod.POST })
    private ResponseEntity<List<String>> catchRequest(HttpServletRequest request) {

        String requestHeader = mapRequestToString(request);
        list.add("PRINTING REQUEST");
        list.add("++++++++++++++++++++=================++++++++++++++++++++");
        list.add(requestHeader);
        list.add("HEADERS PRINTED, ================ PRINTING PAYLOAD");
        try {

            list.add(getBody(request));
        }
        catch (Exception e) {
            list.add("Exception occurred");
        }
        list.add("REQUEST PRINTED");
        list.add("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        return new ResponseEntity<>(list, HttpStatus.OK);
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

    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
