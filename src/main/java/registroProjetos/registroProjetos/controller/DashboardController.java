package registroProjetos.registroProjetos.controller;

import registroProjetos.registroProjetos.dto.response.DashboardResponseDTO;
import registroProjetos.registroProjetos.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponseDTO> gerarDashboard() {
        return ResponseEntity.ok(dashboardService.gerarDashboard());
    }
}