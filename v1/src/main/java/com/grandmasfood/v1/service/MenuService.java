package com.grandmasfood.v1.service;

import org.springframework.stereotype.Service;

@Service
public interface MenuService {

    byte[] generateMenu(String contentTypeHeader) throws Exception;
}
