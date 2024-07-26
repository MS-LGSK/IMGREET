package com.lgsk.imgreet.share.service;

import com.lgsk.imgreet.share.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final ShareRepository shareRepository;

    public String encodingId(long greet_id) {
        // URL 인코딩을 통해 id 값 암호화
        String encodingId;
        try {
            encodingId = URLEncoder.encode(String.valueOf(greet_id), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        return "";
    }

}
