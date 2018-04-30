package org.ovirt.engine.api.restapi.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.ovirt.engine.api.model.Action;
import org.ovirt.engine.api.model.Job;
import org.ovirt.engine.core.common.action.ActionParametersBase;
import org.ovirt.engine.core.common.action.ActionType;
import org.ovirt.engine.core.common.action.EndExternalJobParameters;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.common.queries.QueryType;

public class BackendJobResourceTest
        extends AbstractBackendSubResourceTest<Job, org.ovirt.engine.core.common.job.Job, BackendJobResource> {

    public BackendJobResourceTest() {
        super(new BackendJobResource(GUIDS[0].toString()));
    }

    @Test
    public void testGetNotFound() throws Exception {
        setUriInfo(setUpBasicUriExpectations());
        setUpGetEntityExpectations(true);
        try {
            resource.get();
            fail("expected WebApplicationException");
        } catch (WebApplicationException wae) {
            verifyNotFoundException(wae);
        }
    }

    @Test
    public void testGet() throws Exception {
        setUriInfo(setUpBasicUriExpectations());
        setUpGetEntityExpectations();

        verifyModel(resource.get(), 0);
    }

    @Test
    public void testEnd() throws Exception {
        setUriInfo(setUpActionExpectations(ActionType.EndExternalJob,
                EndExternalJobParameters.class,
              new String[] { "JobId", "Status", "Force" },
              new Object[] { GUIDS[0], true, false }, true, true));
        Action action = new Action();
        action.setSucceeded(true);
        verifyActionResponse(resource.end(action));
    }

    @Test
    public void testClear() throws Exception {
        setUriInfo(setUpActionExpectations(ActionType.ClearExternalJob,
                ActionParametersBase.class,
              new String[] {"JobId"},
              new Object[] {GUIDS[0]}, true, true));
        Action action = new Action();
        verifyActionResponse(resource.clear(action));
    }

    protected void setUpGetEntityExpectations() throws Exception {
        setUpGetEntityExpectations(false);
    }

    protected void setUpGetEntityExpectations(boolean notFound) throws Exception {
        setUpGetEntityExpectations(QueryType.GetJobByJobId,
                IdQueryParameters.class,
                new String[] { "Id" },
                new Object[] { GUIDS[0] },
                notFound ? null : getEntity(0));
    }

    @Override
    protected org.ovirt.engine.core.common.job.Job getEntity(int index) {
        org.ovirt.engine.core.common.job.Job job = new org.ovirt.engine.core.common.job.Job();
        job.setId(GUIDS[index]);
        job.setDescription(DESCRIPTIONS[index]);
        job.setExternal(true);
        job.setAutoCleared(true);
        job.setStartTime(new Date());
        return job;
    }

    @Override
    protected void verifyModel(Job model, int index) {
        assertEquals(GUIDS[index].toString(), model.getId());
        assertEquals(DESCRIPTIONS[index], model.getDescription());
        assertTrue(model.isExternal());
        assertTrue(model.isAutoCleared());
        verifyLinks(model);
    }

    private void verifyActionResponse(Response r) throws Exception {
        verifyActionResponse(r, "jobs/" + GUIDS[0], false);
    }
}
