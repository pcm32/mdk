/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.io.service;

import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.ebi.resource.chemical.ChEBIIdentifier;

/**
 *
 * @author pmoreno
 */
public class ChEBINameServiceTest {
    
    public ChEBINameServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ChEBINameService.
     *
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ChEBINameService expResult = null;
        ChEBINameService result = ChEBINameService.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of fuzzySearchForName method, of class ChEBINameService.
     *
    @Test
    public void testFuzzySearchForName() {
        System.out.println("fuzzySearchForName");
        String name = "";
        ChEBINameService instance = null;
        Collection expResult = null;
        Collection result = instance.fuzzySearchForName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchForName method, of class ChEBINameService.
     *
    @Test
    public void testSearchForName() {
        System.out.println("searchForName");
        String name = "";
        ChEBINameService instance = null;
        Collection expResult = null;
        Collection result = instance.searchForName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPreferredName method, of class ChEBINameService.
     */
    @Test
    public void testGetPreferredName() {
        System.out.println("getPreferredName");
        ChEBIIdentifier identifier = new ChEBIIdentifier(45);
        ChEBINameService instance = ChEBINameService.getInstance();
        String expResult = "(+)-Quercitol";
        String result = instance.getPreferredName(identifier);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSynonyms method, of class ChEBINameService.
     */
    @Test
    public void testGetSynonyms() {
        System.out.println("getSynonyms");
        ChEBIIdentifier identifier = new ChEBIIdentifier(95);
        ChEBINameService instance = ChEBINameService.getInstance();
        
        Collection result = instance.getSynonyms(identifier);
        assertTrue(result.contains("Jasmonate"));
        assertTrue(result.contains("Jasmonic acid"));
    }

    /**
     * Test of getNames method, of class ChEBINameService.
     */
    @Test
    public void testGetNames() {
        System.out.println("getNames");
        ChEBIIdentifier identifier = new ChEBIIdentifier(95);
        ChEBINameService instance = ChEBINameService.getInstance();
        
        Collection result = instance.getNames(identifier);
        assertTrue(result.contains("(-)-Jasmonic acid"));
        assertTrue(result.contains("Jasmonate"));
        assertTrue(result.contains("Jasmonic acid"));
    }
}