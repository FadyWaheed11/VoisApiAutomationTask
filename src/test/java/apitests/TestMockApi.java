package apitests;

import apis.MockApi;
import org.junit.Assert;
import org.junit.Test;

public class TestMockApi {
    @Test
    public void testApi() {
        MockApi mockApi = new MockApi(7);
        mockApi.getRandomUserEmailAddress();
        Assert.assertTrue(mockApi.checkPostsIds(mockApi.getAssociatedPostsForRandomUser()));
        Assert.assertEquals(201, mockApi.postRequest());
    }
}
