package com.ntw.oms.cart.dao.cassandra;

import com.ntw.oms.cart.dao.CartDao;
import com.ntw.oms.cart.entity.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("CartCQL")
public class DBCartLineDao implements CartDao {

    private static final Logger logger = LoggerFactory.getLogger(DBCartLineDao.class);

    @Autowired(required = false)
    @Qualifier("cartCassandraOperations")
    private CassandraOperations cassandraOperations;

    @Autowired(required = false)
    @Qualifier("cartCqlTemplate")
    private CqlTemplate cqlTemplate;

    public CassandraOperations getCassandraOperations() {
        return cassandraOperations;
    }

    public void setCassandraOperations(CassandraOperations cassandraOperations) {
        this.cassandraOperations = cassandraOperations;
    }

    public CqlTemplate getCqlTemplate() {
        return cqlTemplate;
    }

    public void setCqlTemplate(CqlTemplate cqlTemplate) {
        this.cqlTemplate = cqlTemplate;
    }

    @Override
    public Cart getCart(String cartId) {
        String cql = "select * from cartline where cartId='"+cartId+"'";
        List<DBCartLine> dbCartLines = getCassandraOperations().
                select(cql, DBCartLine.class);
        if (dbCartLines == null) {
            logger.debug("No cart lines found; userId={}", cql);
            return null;
        }
        Cart cart = DBCartLine.getCart(cartId, dbCartLines);
        logger.debug("Fetched cart; context={}", cart);
        return cart;
    }

    @Override
    public boolean saveCart(Cart cart) {
        List<DBCartLine> dbCartLines = DBCartLine.createDBCart(cart);
        CassandraBatchOperations batchOps = cassandraOperations.batchOps();
        batchOps.insert(dbCartLines);
        batchOps.execute();
        logger.debug("Saved cart; context={}", cart);
        return true;
    }

    @Override
    public boolean updateCart(Cart cart) {
        if (! removeCart(cart.getId())) {
            logger.debug("Cart already removed; context={}", cart);
        }
        List<DBCartLine> dbCartLines = DBCartLine.createDBCart(cart);
        CassandraBatchOperations batchOps = cassandraOperations.batchOps();
        batchOps.insert(dbCartLines);
        batchOps.execute();
        logger.debug("Updated cart; context={}", cart);
        return true;
    }

    @Override
    public boolean removeCart(String id) {
        String cql = "delete from cartline where cartId='"+id+"'";
        Cart cart = getCart(id);
        List<DBCartLine> cartLines = DBCartLine.createDBCart(cart);
        CassandraBatchOperations batchOps = cassandraOperations.batchOps();
        batchOps.delete(cartLines);
        batchOps.execute();
        logger.debug("Removed cart; cartId={}", id);
        return true;
    }

    @Override
    public boolean removeCarts() {
        String cql = "truncate userauth";
        cqlTemplate.execute(cql);
        logger.debug("Deleted test data; executed query={}",cql);
        return true;
    }

}
