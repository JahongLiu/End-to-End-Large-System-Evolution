package com.ntw.oms.inventory.dao.sql;

import com.ntw.oms.inventory.entity.InventoryReservationLine;

public class ReservationTxnRetryException extends Exception {
    public ReservationTxnRetryException(InventoryReservationLine line) {
        super((new StringBuilder()).append("Exception due to Race Condition for line item ")
                .append(line.toString()).toString());
    }
}
