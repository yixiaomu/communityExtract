package cn.passionshark.project.community.common.util;


public abstract class MaskSupport
{
  private static final String PHONE_PATTERN = "(?<=\\d{3})\\d(?=\\d{3})";
  private static final String CARD_PATTERN = "(?<=\\d{4})\\d(?=\\d{4})";
  private static final String IDENTITY_PATTERN = "(?<=\\d{4})\\d(?=\\d{3})";
  private static final int ID_PERFIX = 4;
  private static final int ID_SUFFIX = 3;

  public static String maskCardNo(String cardNo)
  {
    if (StringUtil.isEmpty(cardNo)) {
      return "";
    }
    return cardNo.replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "*");
  }

  public static String maskIdInfo(String idNo) {
    if (StringUtil.isEmpty(idNo)) {
      return "";
    }

    int len = 7;
    StringBuffer rc = new StringBuffer();
    if (idNo.length() > len) {
      return idNo.replaceAll("(?<=\\d{4})\\d(?=\\d{3})", "*");
    }
    if (idNo.length() > 3) {
      rc.append(idNo.substring(0, 3)).append("***");
    }
    else {
      rc.append("***");
    }

    return rc.toString();
  }

  public static String maskEmail(String email) {
    if (StringUtil.isEmpty(email)) {
      return "";
    }
    try
    {
      int atpos = email.lastIndexOf("@");
      if (-1 == atpos) {
        throw new IllegalArgumentException("邮件格式不正确");
      }

      int pointpos = email.substring(atpos).lastIndexOf(".");
      if (-1 == pointpos) {
        throw new IllegalArgumentException("邮件格式不正确");
      }

      StringBuffer rc = new StringBuffer();
      if (atpos > 3) {
        rc.append(email.substring(0, 3));
        int len = email.substring(0, atpos).length();
        for (int i = 3; i < len; i++)
          rc.append("*");
      }
      else
      {
        rc.append("***");
      }
      rc.append("@");
      rc.append(email.substring(atpos + 1));
      return rc.toString(); } catch (Exception e) {
    }
    return "";
  }

  public static String maskPhone(String phone)
  {
    if (StringUtil.isEmpty(phone)) {
      return "";
    }

    return phone.replaceAll("(?<=\\d{3})\\d(?=\\d{3})", "*");
  }
}