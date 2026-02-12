package com.grandmasfood.v1.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface MenuService {

    byte[] generateMenu(String contentTypeHeader) throws Exception;
}
