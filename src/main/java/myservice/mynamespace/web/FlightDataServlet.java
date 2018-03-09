package myservice.mynamespace.web;

import myservice.mynamespace.database.service.DummyDataCreator;
import myservice.mynamespace.database.service.CRUDHandler;
import myservice.mynamespace.service.FlightDataEdmProvider;
import myservice.mynamespace.service.FlightDataEntityCollectionProcessor;
import myservice.mynamespace.service.FlightDataEntityProcessor;
import myservice.mynamespace.service.FlightDataPrimitiveProcessor;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class FlightDataServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(FlightDataServlet.class);

    public void init() throws ServletException {
        LOG.info("Creating dummy data if database is empty.");
        // creating some sample data
        DummyDataCreator.createTestData();
        LOG.info("Dummy data created.");//TODO verschieben
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final HttpSession session = req.getSession(true);
            CRUDHandler CRUDHandler = (CRUDHandler) session.getAttribute(CRUDHandler.class.getName());
            if (CRUDHandler == null) {
                CRUDHandler = new CRUDHandler();
                session.setAttribute(CRUDHandler.class.getName(), CRUDHandler);
            }

            // create odata handler and configure it with FlightDataEdmProvider and Processor
            final OData odata = OData.newInstance();
            final ServiceMetadata edm = odata.createServiceMetadata(new FlightDataEdmProvider(), new ArrayList<>());
            final ODataHttpHandler handler = odata.createHandler(edm);
            handler.register(new FlightDataEntityCollectionProcessor(CRUDHandler));
            handler.register(new FlightDataEntityProcessor(CRUDHandler));
            handler.register(new FlightDataPrimitiveProcessor(CRUDHandler));

            // let the handler do the work
            handler.process(req, resp);
        } catch (RuntimeException e) {
            LOG.error("Server Error occurred in FlightDataServlet", e);
            throw new ServletException(e);
        }
    }
}
