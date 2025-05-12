package com.ntw.oms.user.dao.cassandra;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class DBAddressKey {

    @PrimaryKeyColumn(name = "userProfileId", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userProfileId;
    @PrimaryKeyColumn(name = "type", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String type;
    @PrimaryKeyColumn(name = "id", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private int id;

    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId = userProfileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userProfileId\":" + (userProfileId == null ? "null" : "\"" + userProfileId + "\"") + ", " +
                "\"type\":" + (type == null ? "null" : "\"" + type + "\"") + ", " +
                "\"id\":\"" + id + "\"" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBAddressKey that = (DBAddressKey) o;

        if (id != that.id) return false;
        if (!userProfileId.equals(that.userProfileId)) return false;
        return type.equals(that.type);

    }

    @Override
    public int hashCode() {
        int result = userProfileId.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + id;
        return result;
    }
}
