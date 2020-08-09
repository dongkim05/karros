package com.karros.vn.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.karros.vn.annotation.UnsignedAccess;
import com.karros.vn.model.Msg;
import com.karros.vn.model.MsgModel;
import com.karros.vn.model.User;
import com.karros.vn.model.UserSession;
import com.karros.vn.model.exception.InvalidTokenException;
import com.karros.vn.model.exception.UnsignedException;
import com.karros.vn.utils.Const;
import com.karros.vn.utils.I18N;
import com.karros.vn.utils.JwtUtils;


@Component
public class AppContextInterceptor implements HandlerInterceptor {
  @Autowired 
  private ResourceBundleMessageSource messageSource;

  @Autowired 
  private JwtUtils jwtUtils;

  private static Logger logger = LoggerFactory.getLogger(AppContextInterceptor.class);

  @Override
  public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    I18N.messageSource = messageSource;
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    response.addHeader("Access-Control-Allow-Headers", "Authorization");
    response.setHeader("Access-Control-Allow-Headers", 
        "Access-Control-Allow-Headers, Origin,Accept, "
            + "X-Requested-With, Content-Type, Access-Control-Request-Method, "
            + "Access-Control-Request-Headers,authorization");
    String apiToken = request.getHeader(Const.Token.API_TOKEN_HEADER);
    HttpSession httpSession = request.getSession(false);
    //Access-Control-Allow-Credentials
    UserSession session = null;
    if(httpSession != null) {
      session =(UserSession) httpSession.getAttribute(Const.User.USER_KEY);
    }

    if(!StringUtils.isEmpty(apiToken)) {
      int valid = jwtUtils.isJwtApiTokenValid(apiToken);
      if(valid == Const.Token.TOKEN_EXPIRED) {
        throw new InvalidTokenException(new MsgModel(new Msg("ERROR00008", I18N.msg("error.token.ERROR00008"))));
      }else if(valid == Const.Token.TOKEN_INVALID) {
        throw new InvalidTokenException(new MsgModel(new Msg("ERROR00009", I18N.msg("error.token.ERROR00009"))));
      }else if(session == null){
        session = new UserSession();
        User user = new User();
        String info = jwtUtils.getInfoFromJwtApiToken(apiToken);
        user.setId(Integer.parseInt(info));
        session.setUser(user);
        httpSession = request.getSession(true);
        httpSession.setAttribute(Const.User.USER_KEY, session);
      }
    }
    if("/error".equals(request.getRequestURI())) {
      return true;
    }

    logger.debug("Pre Handle method is Calling" + handler.getClass());

    if(session == null) {
      if (handler instanceof HandlerMethod) {
        HandlerMethod method = (HandlerMethod) handler;
        UnsignedAccess ann =method.getMethodAnnotation(UnsignedAccess.class);
        if(ann == null) {
          throw new UnsignedException(new MsgModel(new Msg("ERROR00002", I18N.msg("error.system.ERROR00002",I18N.msg("pharse.karros")))));
        }

      }
    }

    return true;
  }
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, 
      Object handler, ModelAndView modelAndView) throws Exception {

  }
  @Override
  public void afterCompletion
  (HttpServletRequest request, HttpServletResponse response, Object 
      handler, Exception exception) throws Exception {
    logger.debug((String) request.getAttribute("JSESSIONID"));
    if(request.getSession(false) != null) {
      logger.debug(request.getSession(false).getId());
    }
  }
}
