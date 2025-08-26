package com.banking.core.service;

import com.banking.core.dto.TransactionDTO;
import java.util.List;

public interface TransactionService {

    List<TransactionDTO> getTransactionsByAccount(String accountNumber);

}
