package org.rest.exceptions;
// TODO - run AppException
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.ExceptionMapper;
//
//public class AppExceptionMapper implements ExceptionMapper<AppException>
//{
//
//    public Response toResponse(AppException ex)
//    {
//        return Response.status(ex.getStatus()).entity(new ErrorMessage(ex)).type(MediaType.APPLICATION_JSON).
//                build();
//    }
//
//}

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AppExceptionMapper implements ExceptionMapper<Exception>
{
    @Override
    public Response toResponse(Exception e)
    {
        e.printStackTrace();
        return Response.status(500).entity(e.toString()).build();
    }
}