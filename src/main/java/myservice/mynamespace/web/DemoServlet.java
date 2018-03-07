
package myservice.mynamespace.web;

import myservice.mynamespace.database.data.CRUDHandler;
import myservice.mynamespace.service.DemoEdmProvider;
import myservice.mynamespace.service.DemoEntityCollectionProcessor;
import myservice.mynamespace.service.DemoEntityProcessor;
import myservice.mynamespace.service.DemoPrimitiveProcessor;
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

public class DemoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(DemoServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final HttpSession session = req.getSession(true);
            CRUDHandler CRUDHandler = (CRUDHandler) session.getAttribute(CRUDHandler.class.getName());
            if (CRUDHandler == null) {
                CRUDHandler = new CRUDHandler();
                session.setAttribute(CRUDHandler.class.getName(), CRUDHandler);
            }

            // create odata handler and configure it with EdmProvider and Processor
            final OData odata = OData.newInstance();
            final ServiceMetadata edm = odata.createServiceMetadata(new DemoEdmProvider(), new ArrayList<>());
            final ODataHttpHandler handler = odata.createHandler(edm);
            handler.register(new DemoEntityCollectionProcessor(CRUDHandler));
            handler.register(new DemoEntityProcessor(CRUDHandler));
            handler.register(new DemoPrimitiveProcessor(CRUDHandler));

            // let the handler do the work
            handler.process(req, resp);
        } catch (RuntimeException e) {
            LOG.error("Server Error occurred in DemoServlet", e);
            throw new ServletException(e);
        }

    }

}
