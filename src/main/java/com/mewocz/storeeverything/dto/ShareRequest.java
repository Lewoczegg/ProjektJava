package com.mewocz.storeeverything.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShareRequest {
    @NotEmpty
    private Long id;

    @NotEmpty
    private String userName;
}
