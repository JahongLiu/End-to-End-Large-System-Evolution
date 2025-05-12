package com.ntw.oms.inventory.dao.sql;

import com.ntw.oms.inventory.dao.InventoryDao;
import com.ntw.oms.inventory.entity.Inventory;
import com.ntw.oms.inventory.entity.InventoryReservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("SQL")
public class DBInventoryDao implements InventoryDao {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    ReservationTxnManagerFactory reservationTxnManagerFactory;

    @Value("${database.type}")
    private String inventoryDBType;

    private ReservationTxnManager reservationTxnManager;

    @PostConstruct
    public void postConstruct()
    {
        this.reservationTxnManager = reservationTxnManagerFactory.getInstance(jdbcTemplate);
    }

    private static final Logger logger = LoggerFactory.getLogger(DBInventoryDao.class);

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTxnManager getReservationTxnManager() {
        return reservationTxnManager;
    }

    public void setReservationTxnManager(ReservationTxnManager reservationTxnManager) {
        this.reservationTxnManager = reservationTxnManager;
    }

    private static final String invSelectSql = "select productId, quantity from inventory where productId=?";

    @Override
    public List<Inventory> getInventory() {
        String allInventorySql = "select * from Inventory";
        List<Inventory> inventoryList = jdbcTemplate.query(allInventorySql,
                new BeanPropertyRowMapper<>(Inventory.class));
        if (inventoryList == null) {
            logger.error("Unable to get all Inventory; context={}", allInventorySql);
            return null;
        }
        logger.debug("Fetched {} inventoryList", inventoryList.size());
        logger.debug("Fetched inventoryList; context={}", inventoryList);
        return inventoryList;
    }

    @Override
    public Inventory getInventory(String id) {
        Inventory inventory;
        try {
            inventory = jdbcTemplate.queryForObject(invSelectSql,
                    new Object[]{id}, new BeanPropertyRowMapper<>(Inventory.class));
        } catch (EmptyResultDataAccessException ee) {
            logger.debug("No inventory found; productId={}", id);
            return null;
        }
        logger.debug("Fetched inventory; context={}", inventory);
        return inventory;
    }

    private static final String invInsertSql = "insert into Inventory (productId, quantity) values(?,?)";

    @Override
    public boolean insertInventory(Inventory inventory) {
        int retValue = jdbcTemplate.update(invInsertSql,
                new Object[] {inventory.getProductId(), inventory.getQuantity()});
        if (retValue <= 0) {
            logger.error("Unable to insert inventory; productId={}, quantity={}",
                    inventory.getProductId(), inventory.getQuantity());
            return false;
        }
        logger.debug("Inserted inventory; productId={}, quantity={}",
                inventory.getProductId(), inventory.getQuantity());
        return true;
    }

    private static final String SQL_DELETE_INVENTORY = "delete from inventory";

    @Override
    public boolean deleteInventory() {
        try {
            jdbcTemplate.update(SQL_DELETE_INVENTORY);
        } catch (Exception e) {
            logger.error("Unable to delete inventory records");
            logger.error("Exception message: ", e);
            return false;
        }
        logger.debug("Deleted all inventory records");
        return true;
    }

    private static final String invUpdateSql = "update Inventory set quantity=? where productId=?";

    @Override
    public boolean updateInventory(Inventory inventory) {
        int retValue;
        try {
            retValue = jdbcTemplate.update(invUpdateSql,
                    new Object[]{inventory.getQuantity(), inventory.getProductId()});
        } catch(Exception e) {
            logger.error("Exception while updating inventory context={}", inventory);
            logger.error("Exception message: ", e);
            return false;
        }
        if (retValue <= 0) {
            logger.error("Unable to update inventory; context={}", inventory);
            return false;
        }
        logger.debug("Updated inventory; productId={}, context={}", inventory);
        return true;
    }

    @Override
    public boolean reserveInventory(InventoryReservation inventoryReservation) {
        return getReservationTxnManager().reserveInventory(inventoryReservation);
    }
}
