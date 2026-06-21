package com.example.backend.controller;

import com.example.backend.dto.request.CreateTransferRequest;
import com.example.backend.dto.request.GetTransfersRequest;
import com.example.backend.dto.response.CreateTransferResponse;
import com.example.backend.dto.response.GetTransfersResponse;
import com.example.backend.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("transfers")
@RestController
public class TransferController {

    private final TransferService service;

    public TransferController(final TransferService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<GetTransfersResponse> getTransfers() {
        GetTransfersRequest request = new GetTransfersRequest();
        GetTransfersResponse response = service.getTransfers(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CreateTransferResponse> registerTransfer(
            @RequestBody final CreateTransferRequest request) {

        CreateTransferResponse response =
                service.createTransfer(request);

        return ResponseEntity.ok(response);
    }
}
