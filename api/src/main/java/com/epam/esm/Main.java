package com.epam.esm;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;

public class Main {
    public static void main(String[] args) {
        TagController tagController = new TagController();
        tagController.showTagList().stream().forEach(System.out::println);

        TagDto tagDto = new TagDto();
        tagDto.setName("Tag555");
        tagController.addNewTag(tagDto);
    }
}
