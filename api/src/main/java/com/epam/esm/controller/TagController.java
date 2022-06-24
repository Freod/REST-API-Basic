package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/tag")
public class TagController {
    @GetMapping("/show/{id}")
    public String showTag(@PathVariable BigInteger id) {
        return id.toString();
    }

    @GetMapping("/showList")
    public String showTagList() {
        return "list";
    }

    @PostMapping("/new")
    public TagDto addNewTag(@RequestBody TagDto tagDto) {
        System.out.println(tagDto);
        return tagDto;
    }

    @PutMapping("/update/{id}")
    public void updateTag(@PathVariable BigInteger id, @RequestBody TagDto tagDto) {
        System.out.println(id + " " + tagDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTag(@PathVariable BigInteger id) {
        System.out.println("Delete" + id);
    }
}
