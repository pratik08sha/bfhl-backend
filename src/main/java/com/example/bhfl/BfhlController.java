package com.example.bhfl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/bfhl")

public class BfhlController {

    @GetMapping
    public ResponseEntity<GetResponseData> getOperationCode() {
        GetResponseData responseData = new GetResponseData(1);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> processData(@RequestBody RequestData requestData) {

        if (!validateRequestData(requestData))
            return new ResponseEntity<>("Invalid input: 'data' field is required and cannot be empty.", HttpStatus.BAD_REQUEST);

        try {

            ResponseData response = new ResponseData();
            response.setIs_success(true);
            response.setUser_id("cheryl_sharma_21bce8624");  // Replace with dynamic values
            response.setEmail("cheryl.21bce8624@vitapstudent.ac.in");  // Replace with dynamic values
            response.setRoll_number("21bce8624");  // Replace with dynamic values

            List<String> numbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> highestLowercase = new ArrayList<>();
            String highest = null;

            for (String item : requestData.getData()) {

                if (item.matches("\\d+")) {
                    numbers.add(item);
                } else if (item.matches("[a-zA-Z]")) {
                    alphabets.add(item);
                    if (item.matches("[a-z]")) {
                        if (highest == null || item.compareTo(highest) > 0) {
                            highest = item;
                        }
                    }
                }
            }

            if (highest != null) {
                highestLowercase.add(highest);
            }

            response.setNumbers(numbers);
            response.setAlphabets(alphabets);
            response.setHighest_lowercase_alphabet(highestLowercase);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>("An error occurred while processing the request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Boolean validateRequestData(RequestData requestData){
        if (requestData == null || requestData.getData() == null || requestData.getData().isEmpty()) {
            return Boolean.FALSE;
        }
        for (String item : requestData.getData()) {
            if (item == null || item.trim().isEmpty()) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}

