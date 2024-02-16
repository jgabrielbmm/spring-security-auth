package com.bentokoder.bentokoder.dtos;

import com.bentokoder.bentokoder.models.ApplicationUser;

public record LoginResponseDTO(ApplicationUser user, String token) {
}
