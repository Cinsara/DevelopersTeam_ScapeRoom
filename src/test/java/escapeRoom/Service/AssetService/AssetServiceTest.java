package escapeRoom.Service.AssetService;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Model.AssetsArea.CertificateBuilder.Certificate;
import escapeRoom.Model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.Model.AssetsArea.TicketBuilder.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AssetServiceTest {

    static private CertificateService certifService;
    static private RewardService rewardService;
    static private TicketService ticketService;

    @BeforeAll
    static void setUp() throws SQLException {
        certifService = new CertificateService(ConnectionManager.getConnection());
        rewardService = new RewardService(ConnectionManager.getConnection());
        ticketService = new TicketService(ConnectionManager.getConnection());
    }

    @Test
    void create() throws SQLException {
        Ticket ticket = new Ticket(1,1, 25.6F, LocalDate.now());
        Reward reward = new Reward(1,1);
        Certificate certificate = new Certificate(1,1);
        ticketService.create(ticket);
        rewardService.create(reward);
        certifService.create(certificate);
    }

    @Test
    void read() throws SQLException {
        Ticket ticket = ticketService.read(1).get();
        Reward reward = rewardService.read(1).get();
        Certificate certificate = certifService.read(1).get();
        assertEquals(25, ticket.getPrice());
        assertEquals(1,reward.getGame_id());
        assertEquals(1, certificate.getGame_id());
    }

    @Test
    void update() throws SQLException {
        Ticket ticket = new Ticket(1,1, 25.6F, LocalDate.now());
        ticket.setId(1);
        Reward reward = new Reward(1,1);
        reward.setId(1);
        Certificate certificate = new Certificate(1,1);
        certificate.setId(1);
        ticketService.update(ticket);
        rewardService.update(reward);
        certifService.update(certificate);

    }

    @Test
    void delete() throws SQLException {
        certifService.delete(1);
        rewardService.delete(1);
        ticketService.delete(15);
    }
}