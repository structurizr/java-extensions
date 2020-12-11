package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.*;

import org.hamcrest.core.AllOf;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import com.structurizr.io.plantuml.*;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class C4PlantUMLWriterTests {
	public static class ValidateURLCanonicalizer {
	    @Test
	    public void test_can_validate_a_raw_url() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.canonicalizeUrl("https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/"), 
	    			Is.is(new URL("https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/")));
	    }
	    @Test
	    public void test_can_validate_a_github_url() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.canonicalizeUrl("https://github.com/plantuml-stdlib/C4-PlantUML/tree/master"), 
	    			Is.is(new URL("https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/")));
	    }
	    @Test
	    public void test_can_validate_a_github_branchless_url() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.canonicalizeUrl("https://github.com/plantuml-stdlib/C4-PlantUML"), 
	    			Is.is(new URL("https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/")));
	    }
	}
	public static class ValidateLibraryInferer {
	    @Test
	    public void test_can_build_the_corect_library() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.inferC4PlantUMLLibraryFrom("https://github.com/plantuml-stdlib/C4-PlantUML"),
	    					hasItems(
	    							"https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml",
	    							"https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml",
	    							"https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml",
	    							"https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml"
	    							)
	    			);
	    }
	}
}
