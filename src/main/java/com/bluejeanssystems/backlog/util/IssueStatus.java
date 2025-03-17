package com.bluejeanssystems.backlog.util;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

public enum IssueStatus {
    未対応,
    対応中,
    対応済み,
    完了;
}
