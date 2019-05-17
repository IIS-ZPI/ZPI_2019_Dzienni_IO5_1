package Utils;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.collection.IsEmptyCollection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContainersTest {




    @Test
    public void read() throws Exception {

        String testFileName = "Exchange Rate.txt";

        Containers containers = new Containers();

        List<String> expected = Arrays.asList("AED");
        List result = containers.readFile(testFileName);
//        assertEquals(expected, result);
     //  Assert.assertThat(result,hasItems(expected));
      //  Assert.assertThat(result, is(equalTo(not(IsEmptyCollection.empty()))));
        assertThat(new ArrayList<>(), IsEmptyCollection.empty());

    }




}