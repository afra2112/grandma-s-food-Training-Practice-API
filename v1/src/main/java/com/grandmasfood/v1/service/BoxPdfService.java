package com.grandmasfood.v1.service;


import org.springframework.stereotype.Service;
import java.io.IOException;


@Service
public interface BoxPdfService {

    byte[] generatePdfMenu() throws IOException;
}
