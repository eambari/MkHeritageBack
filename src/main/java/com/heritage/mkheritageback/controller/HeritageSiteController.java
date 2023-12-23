package com.heritage.mkheritageback.controller;

import com.heritage.mkheritageback.model.HeritageSite;
import com.heritage.mkheritageback.service.HeritageSiteService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/heritage")
public class HeritageSiteController {

    private final HeritageSiteService heritageSiteService;
    private final ResourceLoader resourceLoader;

    public HeritageSiteController(HeritageSiteService heritageSiteService, ResourceLoader resourceLoader) {
        this.heritageSiteService = heritageSiteService;
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/create")
    public String createHeritageSite(@RequestBody HeritageSite HeritageSite) throws InterruptedException, ExecutionException {
        return heritageSiteService.createHeritageSite(HeritageSite);
    }

    @GetMapping("/all")
    public List<HeritageSite> getAllHeritageSites() throws Exception {
        return heritageSiteService.getAllHeritageSites();
    }

    @GetMapping("/get/{id}")
    public HeritageSite getHeritageSiteById(@PathVariable String id) throws InterruptedException, ExecutionException {
        return heritageSiteService.getHeritageSiteById(id);
    }

    // put the HeritageSite name in a query string
    @GetMapping("/get")
    public List<HeritageSite> getHeritageSiteByName(@RequestParam String name) throws ExecutionException, InterruptedException {
        return heritageSiteService.getHeritageSiteByName(name);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteHeritageSite(@PathVariable String id) {
        return heritageSiteService.deleteHeritageSite(id);
    }

    @PutMapping("/update/{id}")
    public String updateHeritageSite(@RequestBody HeritageSite HeritageSite) throws ExecutionException, InterruptedException {
        return heritageSiteService.updateHeritageSite(HeritageSite);
    }
}
