package com.berrontech.weight.update.controller;

import com.berrontech.weight.update.upgrade.UpgradeException;
import com.berrontech.weight.update.upgrade.Upgrader;
import com.berrontech.weight.update.upgrade.dto.AppVersion;
import com.berrontech.weight.update.vo.UpgradeParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * Create By Levent8421
 * Create Time: 2020/12/2 14:18
 * Class Name: UpgradeController
 * Author: Levent8421
 * Description:
 * Upgrade Controller
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/upgrade")
public class UpgradeController {
    private final Upgrader upgrader;

    public UpgradeController(Upgrader upgrader) {
        this.upgrader = upgrader;
    }

    @PostMapping("/")
    public Object upgrade(@RequestBody UpgradeParam param) throws UpgradeException {
        final AppVersion version = upgrader.getLastVersion();
        if (upgrader.shouldUpgrade(version, param.getVersionCode())) {
            final File file = upgrader.downloadUpgradePackage(version);
            upgrader.doUpgrade(file);
            return "upgrade";
        }
        return "Skip";
    }
}
