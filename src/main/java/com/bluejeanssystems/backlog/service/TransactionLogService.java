package com.bluejeanssystems.backlog.service;

import com.bluejeanssystems.backlog.model.Issue;
import com.bluejeanssystems.backlog.model.TransactionLog;
import com.bluejeanssystems.backlog.repository.IssueRepository;
import com.bluejeanssystems.backlog.repository.TransactionLogRepository;
import com.bluejeanssystems.backlog.util.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionLogService {

    private final TransactionLogRepository transactionLogRepository;
    private final IssueRepository issueRepository;

    public TransactionLog createTransactionLog(
            Long projectId,
            Long issueNumber,
            TransactionType transactionType,
            String message) {

        Issue issue = issueRepository.findByIssueNumber(projectId, issueNumber);

        var log = new TransactionLog();
        log.setIssue(issue);
        log.setTransactionType(transactionType);
        log.setMessage(message);

        return transactionLogRepository.save(log);
    }
}
