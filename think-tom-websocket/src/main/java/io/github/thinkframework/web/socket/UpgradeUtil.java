package io.github.thinkframework.web.socket;

import io.github.thinkframework.adapter.http11.upgrade.Http11UpgradeHandler;
import io.github.thinkframework.util.codec.binary.Base64;
import io.github.thinkframework.util.security.ConcurrentMessageDigest;
import io.github.thinkframework.web.socket.server.WsHandshakeRequest;
import io.github.thinkframework.web.socket.server.WsServerContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class UpgradeUtil {
    private static final byte[] WS_ACCEPT =
            "258EAFA5-E914-47DA-95CA-C5AB0DC85B11".getBytes(
                    StandardCharsets.ISO_8859_1);



    public static void doUpgrade(WsServerContainer sc, HttpServletRequest req,
                                 HttpServletResponse resp, ServerEndpointConfig sec,
                                 Map<String,String> pathParams)
            throws ServletException, IOException {

//        // Validate the rest of the headers and reject the request if that
//        // validation fails
        String key;
//        String subProtocol = null;
//        if (!headerContainsToken(req, Constants.CONNECTION_HEADER_NAME,
//                Constants.CONNECTION_HEADER_VALUE)) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        if (!headerContainsToken(req, Constants.WS_VERSION_HEADER_NAME,
//                Constants.WS_VERSION_HEADER_VALUE)) {
//            resp.setStatus(426);
//            resp.setHeader(Constants.WS_VERSION_HEADER_NAME,
//                    Constants.WS_VERSION_HEADER_VALUE);
//            return;
//        }
        key = req.getHeader(Constants.WS_KEY_HEADER_NAME);
//        if (key == null) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//
//
//        // Origin check
//        String origin = req.getHeader(Constants.ORIGIN_HEADER_NAME);
//        if (!sec.getConfigurator().checkOrigin(origin)) {
//            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }
//        // Sub-protocols
//        List<String> subProtocols = getTokensFromHeader(req,
//                Constants.WS_PROTOCOL_HEADER_NAME);
//        subProtocol = sec.getConfigurator().getNegotiatedSubprotocol(
//                sec.getSubprotocols(), subProtocols);
//
//        // Extensions
//        // Should normally only be one header but handle the case of multiple
//        // headers
//        List<Extension> extensionsRequested = new ArrayList<>();
//        Enumeration<String> extHeaders = req.getHeaders(Constants.WS_EXTENSIONS_HEADER_NAME);
//        while (extHeaders.hasMoreElements()) {
//            Util.parseExtensionHeader(extensionsRequested, extHeaders.nextElement());
//        }
//        // Negotiation phase 1. By default this simply filters out the
//        // extensions that the server does not support but applications could
//        // use a custom configurator to do more than this.
//        List<Extension> installedExtensions = null;
//        if (sec.getExtensions().size() == 0) {
//            installedExtensions = Constants.INSTALLED_EXTENSIONS;
//        } else {
//            installedExtensions = new ArrayList<>();
//            installedExtensions.addAll(sec.getExtensions());
//            installedExtensions.addAll(Constants.INSTALLED_EXTENSIONS);
//        }
//        List<Extension> negotiatedExtensionsPhase1 = sec.getConfigurator().getNegotiatedExtensions(
//                installedExtensions, extensionsRequested);
//
//        // Negotiation phase 2. Create the Transformations that will be applied
//        // to this connection. Note than an extension may be dropped at this
//        // point if the client has requested a configuration that the server is
//        // unable to support.
//        List<Transformation> transformations = createTransformations(negotiatedExtensionsPhase1);
//
//        List<Extension> negotiatedExtensionsPhase2;
//        if (transformations.isEmpty()) {
//            negotiatedExtensionsPhase2 = Collections.emptyList();
//        } else {
//            negotiatedExtensionsPhase2 = new ArrayList<>(transformations.size());
//            for (Transformation t : transformations) {
//                negotiatedExtensionsPhase2.add(t.getExtensionResponse());
//            }
//        }
//
//        // Build the transformation pipeline
//        Transformation transformation = null;
//        StringBuilder responseHeaderExtensions = new StringBuilder();
//        boolean first = true;
//        for (Transformation t : transformations) {
//            if (first) {
//                first = false;
//            } else {
//                responseHeaderExtensions.append(',');
//            }
//            append(responseHeaderExtensions, t.getExtensionResponse());
//            if (transformation == null) {
//                transformation = t;
//            } else {
//                transformation.setNext(t);
//            }
//        }
//
//        // Now we have the full pipeline, validate the use of the RSV bits.
//        if (transformation != null && !transformation.validateRsvBits(0)) {
//            throw new ServletException(sm.getString("upgradeUtil.incompatibleRsv"));
//        }

        // If we got this far, all is good. Accept the connection.
        resp.setHeader(Constants.UPGRADE_HEADER_NAME,
                Constants.UPGRADE_HEADER_VALUE);
        resp.setHeader(Constants.CONNECTION_HEADER_NAME,
                Constants.CONNECTION_HEADER_VALUE);
        resp.setHeader(HandshakeResponse.SEC_WEBSOCKET_ACCEPT,
                getWebSocketAccept(key));
//        if (subProtocol != null && subProtocol.length() > 0) {
//            // RFC6455 4.2.2 explicitly states "" is not valid here
//            resp.setHeader(Constants.WS_PROTOCOL_HEADER_NAME, subProtocol);
//        }
//        if (!transformations.isEmpty()) {
//            resp.setHeader(Constants.WS_EXTENSIONS_HEADER_NAME, responseHeaderExtensions.toString());
//        }
//
//        // Add method mapping to user properties
//        if (!Endpoint.class.isAssignableFrom(sec.getEndpointClass()) &&
//                sec.getUserProperties().get(org.apache.tomcat.websocket.pojo.Constants.POJO_METHOD_MAPPING_KEY) == null) {
//            // This is a POJO endpoint and the application has called upgrade
//            // directly. Need to add the method mapping.
//            try {
//                PojoMethodMapping methodMapping = new PojoMethodMapping(sec.getEndpointClass(),
//                        sec.getDecoders(), sec.getPath(), sc.getInstanceManager(Thread.currentThread().getContextClassLoader()));
//                if (methodMapping.getOnClose() != null || methodMapping.getOnOpen() != null
//                        || methodMapping.getOnError() != null || methodMapping.hasMessageHandlers()) {
//                    sec.getUserProperties().put(
//                            org.apache.tomcat.websocket.pojo.Constants.POJO_METHOD_MAPPING_KEY, methodMapping);
//                }
//            } catch (DeploymentException e) {
//                throw new ServletException(
//                        sm.getString("upgradeUtil.pojoMapFail", sec.getEndpointClass().getName()),  e);
//            }
//        }
//
//        WsPerSessionServerEndpointConfig perSessionServerEndpointConfig =
//                new WsPerSessionServerEndpointConfig(sec);
//
        WsHandshakeRequest wsRequest = new WsHandshakeRequest(req);
        WsHandshakeResponse wsResponse = new WsHandshakeResponse(resp);
//        sec.getConfigurator().modifyHandshake(perSessionServerEndpointConfig, wsRequest, wsResponse);
//        wsRequest.finished();

        // Add any additional headers
        for (Map.Entry<String,List<String>> entry :
                wsResponse.getHeaders().entrySet()) {
            for (String headerValue: entry.getValue()) {
                resp.addHeader(entry.getKey(), headerValue);
            }
        }

        Http11UpgradeHandler http11UpgradeHandler =
                req.upgrade(Http11UpgradeHandler.class);
//        wsHandler.preInit(perSessionServerEndpointConfig, sc, wsRequest,
//                negotiatedExtensionsPhase2, subProtocol, transformation, pathParams,
//                req.isSecure());
//        http11UpgradeHandler.init();
    }

    /**
     *
     * @param key
     * @return
     */
    public static String getWebSocketAccept(String key) {
        byte[] digest = ConcurrentMessageDigest.digestSHA1(
                key.getBytes(StandardCharsets.ISO_8859_1), WS_ACCEPT);
        return Base64.encodeBase64String(digest);
    }
}
