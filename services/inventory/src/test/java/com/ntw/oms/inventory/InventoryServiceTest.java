package com.ntw.oms.inventory;

import com.ntw.oms.inventory.entity.Inventory;
import com.ntw.oms.inventory.entity.InventoryReservation;
import com.ntw.oms.inventory.entity.InventoryReservationLine;
import com.ntw.oms.inventory.service.InventoryServiceImpl;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class InventoryServiceTest extends TestCase {

    private static InventoryMockDao inventoryDaoBean;
    private static InventoryServiceImpl inventoryServiceBean;

    public InventoryServiceTest() {
    }

    public InventoryServiceTest(String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( InventoryServiceTest.class );
    }

    public void setUp() {
        inventoryDaoBean = new InventoryMockDao();
        inventoryServiceBean = new InventoryServiceImpl();
        inventoryServiceBean.setInventoryDaoBean(inventoryDaoBean);
    }

    public void testGetInventory() {
        Inventory inventoryGet1 = inventoryServiceBean.getInventory("Non-Existent");
        Assert.assertNull(inventoryGet1);

        String testProductId = "Test-Product-1";
        Inventory inventory = new Inventory();
        inventory.setProductId(testProductId);
        inventory.setQuantity(10);
        inventoryDaoBean.insertInventory(inventory);
        Inventory inventoryGet2 = inventoryServiceBean.getInventory("Test-Product-1");
        Assert.assertEquals(10f, inventoryGet2.getQuantity());
    }

    public void testReserveInventory() {
        String testProductId = "Test-Product-1";
        Inventory inventory = new Inventory();
        inventory.setProductId(testProductId);
        inventory.setQuantity(10);
        inventoryDaoBean.insertInventory(inventory);

        InventoryReservation inventoryReservation = new InventoryReservation();
        inventoryReservation.addInvResLine(testProductId, 3);
        boolean success = inventoryServiceBean.reserveInventory(inventoryReservation);
        Assert.assertTrue(success);
        Inventory inventoryGet = inventoryServiceBean.getInventory(testProductId);
        Assert.assertEquals(7f, inventoryGet.getQuantity());
    }

}
