package org.example.domain;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.io.FileInputStream;
import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.dbmigration.util.IOUtils;

public class OwnerTest {

    private EbeanServer server;

    @BeforeTest
    public void setup() throws IOException {
	final ServerConfig serverConfig = new ServerConfig();
	serverConfig.loadFromProperties();
	serverConfig.setDdlCreateOnly(true);
	serverConfig.setDdlRun(false);
	serverConfig.setH2ProductionMode(true);
	server = EbeanServerFactory.create(serverConfig);
	try (final FileInputStream in = new FileInputStream("db-create-all.sql")) {
	    String createSql = IOUtils.readUtf8(in);
	    String noForeignKey = createSql.replaceAll("alter table owned add constraint .*", "");
	    server.createSqlUpdate(noForeignKey).execute();
	}
    }

    @Test
    public void insert_via_server() {
	Owner1 owner1 = new Owner1();
	owner1.setText("Owner1");
	server.save(owner1);

	Owned owned1 = new Owned(owner1.getId(), 1);
	server.save(owned1);

	Owner1 refereshedOwner1 = server.find(Owner1.class, owner1.getId());
	assertFalse(refereshedOwner1.getOwneds().isEmpty());
	assertEquals(1, refereshedOwner1.getOwneds().size());
	System.err.println("Foo");
	System.out.println("foo");
	Owned refreshedOwned1 = server.find(Owned.class).fetch("owner1").fetch("owner2").where().idEq(owned1.getId()).findUnique();
	assertEquals(owned1.getId(), refreshedOwned1.getId());
	assertNotNull(refreshedOwned1.getOwner1());
	assertEquals(owner1.getId(), refreshedOwned1.getOwner1().getId());
	assertNull(refreshedOwned1.getOwner2(), "Owner2 has text: " + refreshedOwned1.getOwner2().getText());



	Owner2 owner2 = new Owner2();
	owner2.setText("Owner2");
	server.save(owner2);

	Owned owned2 = new Owned(owner2.getId(), 2);
	server.save(owned2);

	Owner2 refereshedOwner2 = server.find(Owner2.class, owner2.getId());
	assertFalse(refereshedOwner2.getOwneds().isEmpty());
	assertEquals(1, refereshedOwner2.getOwneds().size());
	Owned refreshedOwned2 = refereshedOwner2.getOwneds().get(0);
	assertEquals(owned2.getId(), refreshedOwned2.getId());
	assertNotNull(refreshedOwned2.getOwner2());
	assertEquals(owner2.getId(), refreshedOwned2.getOwner2().getId());
	assertNull(refreshedOwned1.getOwner1(), "Owner1 has text: " + refreshedOwned1.getOwner1().getText());

    }
}