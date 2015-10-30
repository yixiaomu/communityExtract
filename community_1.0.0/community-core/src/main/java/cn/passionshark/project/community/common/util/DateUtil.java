package cn.passionshark.project.community.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>
 * A suite of utilities surrounding the use of the {@link java.util.Calendar} and {@link java.util.Date} object.
 * </p>
 * 
 * @author pierre
 * @version $ v1.0 Jan 6, 2015 $
 */
public class DateUtil {
    private static final Logger logger = Logger.getLogger(DateUtil.class.getName());

    /** yyyy年MM月dd日 HH:mm */
    private static final String SHOW_FORMAT = "yyyy年MM月dd日 HH:mm";
    /** yyyyMMddHHmmss */
    private static final String TIGHT_FORM_FORMAT = "yyyyMMddHHmmss";
    /** yyyy-MM-dd */
    private static final String SIMPLE_FORM_FORMAT = "yyyy-MM-dd";
    /** yyyyMMdd */
    private static final String SIMPLE_TIGHT_FORMAT = "yyyyMMdd";

    private DateUtil() {
    }

    /**
     * 字符日期串转换成DATE对象, 会重新创建SimpleDateFormat对象.
     * 
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date str2date(String dateStr, String pattern) {
        if (StringUtil.isEmpty(dateStr) || StringUtil.isEmpty(pattern)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (Exception ex) {
            logger.warning("[DATE] " + ex.getMessage());
            return null;
        }
    }

    /**
     * 字符串转换为形如yyyy年MM月dd日 HH:mm的格式
     * 
     * @param String
     * @return String
     */
    public static String convertToShowTime(String dateStr) {
        if (StringUtil.isEmptyStrnull(dateStr)) {
            return "";
        }

        String ret = dateStr;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(TIGHT_FORM_FORMAT);
            dateFormat.setLenient(false);
            Date date = dateFormat.parse(dateStr);

            SimpleDateFormat showDateFormat = new SimpleDateFormat(SHOW_FORMAT);
            showDateFormat.setLenient(false);
            ret = showDateFormat.format(date);
        } catch (ParseException e) {
            logger.warning("[DATE] " + e.getMessage());
        }

        return ret;
    }

    /**
     * 字符串转换为形如yyyy年MM月dd日 HH:mm的格式
     * 
     * @param Date
     * @return String
     */

    public static String convertToShowTime(Date date) {
        if (date == null) {
            return "";
        }
        String ret = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(SHOW_FORMAT);
            dateFormat.setLenient(false);
            ret = dateFormat.format(date);
        } catch (Exception e) {
            logger.warning("[DATE] " + e.getMessage());
        }

        return ret;
    }

    /**
     * DATE对象转换成字符日期串
     * 
     * @param dateStr
     * @param pattern
     * @return String
     */
    public static String date2str(Date date, String pattern) {
        if (date == null || StringUtil.isEmpty(pattern)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception ex) {
            logger.warning("[DATE] " + ex.getMessage());
            return null;
        }
    }

    /**
     * 转换为yyyyMMddHHmmss.
     * 
     * @param date
     * @return
     */
    public static String convert(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(TIGHT_FORM_FORMAT).format(date);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 将日期格式化为字符串 yyyyMMdd
     * 
     * @param Object
     * @return String
     */
    public static String dateToString(Object date) {
        if (date == null) {
            return "";
        }
        try {
            date = (Date) date;
            return new SimpleDateFormat(SIMPLE_TIGHT_FORMAT).format(date);
        } catch (Exception e) {
            logger.warning("[DATE]" + e.getMessage());
            return "";
        }
    }

    /**
     * 通过指定格式格式化DATE
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateTime(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat fm = new SimpleDateFormat(pattern);
            return fm.format(date);
        } catch (Exception e) {
            logger.warning("[DATE]" + e.getMessage());
            return null;
        }
    }

    /**
     * 返回系统当前的完整日期时间 <br>
     * 格式 1：2008-05-02 13:12:44 <br>
     * 格式 2：2008/05/02 13:12:44 <br>
     * 格式 3：2008年5月2日 13:12:44 <br>
     * 格式 4：2008年5月2日 13时12分44秒 <br>
     * 格式 5：2008年5月2日 星期五 13:12:44 <br>
     * 格式 6：2008年5月2日 星期五 13时12分44秒 <br>
     * 格式 7：20080502 <br>
     * 格式 8：20080502131244 <br>
     * 格式 9：2008-05-02 <br>
     * 格式 10：2008_05 <br>
     * 格式 11：2008 <br>
     * 格式 12：200805 <br>
     * 格式 13：2008-05 <br>
     * 格式 default：yyyyMMddHHmmss:20080502131244 <br>
     * 
     * @param int ,Date 参数(formatType) :格式代码号
     * @return String
     */
    public static String get(int formatType, Date date) {
        if (null == date)
            return null;
        SimpleDateFormat sdf = null;
        switch (formatType) {
        case 1:
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            break;
        case 2:
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            break;
        case 3:
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            break;
        case 4:
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            break;
        case 5:
            sdf = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm:ss");
            break;
        case 6:
            sdf = new SimpleDateFormat("yyyy年MM月dd日 E HH时mm分ss秒");
            break;
        case 7:
            sdf = new SimpleDateFormat("yyyyMMdd");
            break;
        case 8:
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            break;
        case 9:
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            break;
        case 10:
            sdf = new SimpleDateFormat("yyyy_MM");
            break;
        case 11:
            sdf = new SimpleDateFormat("yyyy");
            break;
        case 12:
            sdf = new SimpleDateFormat("yyyyMM");
            break;
        case 13:
            sdf = new SimpleDateFormat("yyyy-MM");
            break;
        default:
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            break;
        }
        // sdf.setLenient(false);
        return sdf.format(date);
    }

    /**
     * 方法说明：字符串日期转Date类型,默认格式（yyyy-MM-dd）
     * 
     * @param String
     * @return Date(yyyy-MM-dd)
     */
    public static Date stringToDate(String strDate) {
        if (strDate != null && !"".equals(strDate)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        } else {
            return null;
        }
    }

    /**
     * 将字符串转换为可查询的Date类型，默认格式(yyyyMMddHHmmss)
     * 
     * @param String
     * @return Date
     * @throws ParseException
     */

    public static Date convertStr2DateForQuery(String source) throws ParseException {
        if (source == null || source.trim().length() != 14)
            return null;

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setLenient(false);
        date = sdf.parse(source);

        return date;
    }

    /**
     * 将字符串转换成时间日期
     * 
     * @param source 格式：yyyymmddHHmmss
     * @return 如果输入的时间比当前时间相差半天，则返回空
     */
    public static Date convertStr2Date(String source) throws ParseException {
        if (source == null || source.trim().length() != 14)
            return null;

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setLenient(false);
        date = sdf.parse(source);

        // 如果输入的时间比当前时间相差半天，则返回空
        long ltemp = date.getTime() - new Date().getTime();
        if (ltemp > 43200000 || ltemp < -43200000) {
            return null;
        }
        return date;
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     * 
     * @param Date
     * @return String (yyyy-MM-dd HH:mm:ss)
     */
    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照参数format的格式，日期转字符串
     * 
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        if (date != null && !StringUtil.isEmptyStrnull(format)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 将日期转换成字符串yyyyMMddHHmmss
     * 
     * @param date
     * @return String
     */
    public static String formatDate2Str(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setLenient(false);
        return sdf.format(date);
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
     * 
     * @param String
     * @return Date
     */
    public static Date str2Date(String date) {
        if (StringUtil.notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setLenient(false);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
            }
            return new Date();
        } else {
            return null;
        }
    }

    public static Date str2Date(String date, String format) {
        if (StringUtil.notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
            }
            return new Date();
        } else {
            return null;
        }
    }

    /**
     * 将接口或页面传递的参数转为<code>java.util.Date</code>.
     * 
     * @param date 日期类型 yyyyMMddHHmmss
     * @return java.util.Date
     */
    public static Date convert(String date) {
        try {
            return new SimpleDateFormat(TIGHT_FORM_FORMAT).parse(date);
        } catch (ParseException e) {
            try {
                return new SimpleDateFormat(SIMPLE_FORM_FORMAT).parse(date);
            } catch (ParseException e1) {
                throw new IllegalArgumentException("无法识别的日期, " + date);
            }
        }
    }

    /***
     * 日期格式转化
     * 
     * @param strDate
     * @param format1
     * @param format2
     * @return
     */
    public static String strConvertDateStr(String strDate, String format1, String format2) {
        if (strDate == null || "".equals(strDate)) {
            return strDate;
        }
        DateFormat df1 = new SimpleDateFormat(format1);
        DateFormat df2 = new SimpleDateFormat(format2);
        df1.setLenient(false);
        df2.setLenient(false);
        String resultDate = "";
        try {
            resultDate = df2.format(df1.parse(strDate));
        } catch (Exception e) {
        }
        return resultDate;
    }

    /***
     * 当前日期加减n天
     * 
     * @param int
     * @param String
     * @return String
     */
    public static String nDayDate(int n, String format1) {
        Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
        cal.add(Calendar.DAY_OF_MONTH, n);// 取当前日期的前一天.cal.add(Calendar.DAY_OF_MONTH,
                                          // +1);//取当前日期的后一天.
        // 通过格式化输出日期
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(format1);
        format.setLenient(false);

        return format.format(cal.getTime());
    }

    /**
     * 返回系统当前的完整日期时间 <br>
     * 格式 1：yyyy年MM月dd日<br>
     * 格式2：yyyy年MM月dd日 HH时mm分ss秒
     * 
     * @param String
     * @param int
     * @return String
     */
    public static String formatDate(String day, int formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(day);

            if (formatType == 1) {
                sdf = new SimpleDateFormat("yyyy年MM月dd日");
            }
            if (formatType == 2) {
                sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            }

            return sdf.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将字符串yyyymmdd日期转化为yyyy-mm-dd 00:00:00:00 yyyy-mm--dd 23:59:59:58 分别作为始末时间放入Map中
     * 
     * @param date
     * @param format
     * @return Map<String, String>
     * @throws ParseException
     */
    public static Map<String, String> dateStrToStr(String dateString) {
        if (dateString != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(dateString.substring(0, 4));
            stringBuffer.append("-");
            stringBuffer.append(dateString.substring(4, 6));
            stringBuffer.append("-");
            stringBuffer.append(dateString.substring(6, 8));
            Map<String, String> map = new HashMap<String, String>();
            map.put("beginTime", stringBuffer + " 00:00:00:00");
            map.put("endTime", stringBuffer + " 23:59:59:58");
            return map;
        } else {
            return null;
        }
    }

    /**
     * 把字符串格式化为yyyy-MM-dd HH:mm:ss形式的时间Date
     * 
     * @param String
     * @return Date
     * @throws ParseException
     */
    public static Date strToDate(String dateString) {
        String lineOneString = dateString.substring(0, dateString.length());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(lineOneString.substring(0, 4));
        stringBuffer.append("-");
        stringBuffer.append(lineOneString.substring(4, 6));
        stringBuffer.append("-");
        stringBuffer.append(lineOneString.substring(6, 8));
        stringBuffer.append(" ");
        stringBuffer.append(dateString.substring(8, 10));
        stringBuffer.append(":");
        stringBuffer.append(dateString.substring(10, 12));
        stringBuffer.append(":");
        stringBuffer.append(dateString.substring(12, 14));
        return str2Date(stringBuffer.toString());
    }

    public static Date strToDate(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /***
     * 把时间格式（YYYY-mm-dd hh:MM:ss）转换成时间戳格式
     * 
     * @param String
     * @return String
     */
    public static String convertStrTimestamp(String str) {
        if (StringUtil.isEmpty(str)) {
            return "";
        }
        String timeStamp = str.replace("-", "");
        timeStamp = timeStamp.replace(":", "");
        timeStamp = timeStamp.replace(" ", "");
        return timeStamp;
    }

    /***
     * 把时间格式（YYYYmmdd）转换成时间(YYYY-mm-dd)格式
     * 
     * @param String
     * @return String
     */
    public static String TimestampToStr(String str) {
        if (StringUtil.isEmpty(str)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, 4));
        stringBuffer.append("-");
        stringBuffer.append(str.substring(4, 6));
        stringBuffer.append("-");
        stringBuffer.append(str.substring(6, 8));
        return stringBuffer.toString();
    }

    /**
     * 把查询到的时间转换成秒
     * 
     * @param url
     * @return
     */
    public static Long changeDateToLong(String dateString, String dateFormt) {

        if (StringUtil.isEmpty(dateString) || StringUtil.isEmpty(dateFormt)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(dateFormt);
        Date date = new Date();
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
        }
        Long dateLong = date.getTime();
        return dateLong / 1000;

    }

    /**
     * 获取当前日期前一个月的时间
     * 
     * @param Date
     * @return String(yyyyMMdd)
     */
    public static String getMonthOne(Date currentTime) {
        if (currentTime == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.MONTH, -1); // 得到前一个月
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String mDateTime = formatter.format(calendar.getTime());
        return mDateTime;
    }

    /**
     * 获取当前日期前一个天的时间
     * 
     * @param Date
     * @return String(yyyyMMdd)
     */
    public static String getOneDay(Date currentTime) {
        if (currentTime == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String mDateTime = formatter.format(calendar.getTime());
        return mDateTime;
    }

    /**
     * 返回：根据模式返回格式化 日期
     * 
     * @param Date
     * @param String
     * @return String
     * @throws ParseException
     */
    public static String getDate(Date date, String dateString) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat(dateString);
        sd.setLenient(false);
        String to_date = sd.format(date);
        return to_date;
    }

    /**
     * 根据类型返回日期，自动根据传入值判断
     * 
     * @param String
     * @return Date
     * @throws ParseException
     */
    public static Date getDate(String strDate) throws ParseException {
        DateFormat format = null;
        if (strDate.contains("-")) {
            format = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            format = new SimpleDateFormat("yyyyMMddHHmmss");
        }
        Date date = null;
        try {
            format.setLenient(false);
            date = format.parse(strDate);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return date;
    }

    /**
     * 返回： 根据传入格式将传入字符串格式化
     * 
     * @param Stirng
     * @param Stirng
     * @return Date
     * @throws ParseException
     */
    public static Date convert2Date(String strDate, String dateString) throws ParseException {
        if (StringUtil.isEmpty(strDate) || StringUtil.isEmpty(dateString)) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(dateString);
        Date date = null;
        format.setLenient(false);
        date = format.parse(strDate);
        return date;
    }

    /**
     * 返回： 根据传入格式将传入字符串格式化
     * 
     * @param Stirng
     * @param Stirng
     * @return Date
     * @throws ParseException
     */
    public static Date convertToDate(String strDate, String dateString) {
        if (StringUtil.isEmpty(strDate) || StringUtil.isEmpty(dateString)) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(dateString);
        Date date = null;
        format.setLenient(false);
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将输入参数格式化为时间戳形式
     * 
     * @param String
     * @return String
     * @throws ParseException
     */
    public static String getTimestamp(String strDate) {
        if (StringUtil.notEmpty(strDate)) {
            return strDate.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").trim();
        } else {
            return "";
        }
    }

    /**
     * 将传入的Date增加或减少天数
     * 
     * @param Date
     * @param int
     * @return Date
     */
    public static Date addDays(Date date, int ndays) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, ndays);

        return calendar.getTime();
    }

    /***
     * 将对象Object转换为Date
     * 
     * @param Object
     * @return Date
     */
    public static Date calStringToDate(Object date) {
        String dateStr = StringUtil.toStringAndTrim(date);

        if (StringUtil.isEmpty(dateStr))
            return null;

        dateStr = dateStr.replaceAll("-", "");

        return new Date(bocmDateToCal(dateStr).getTimeInMillis());

    }

    /**
     * 以输入时间为准，向前滚动需要的天数后返回日期的格式字符串
     * 
     * @param inputDate 需要滚动的基准日期格式为yyyyMMdd
     * @param days 向前滚动的天数
     * @return 返回日期格式 yyyyMMdd
     */
    public static String rolDate(String inputDate, long days) {
        java.util.Date inday = bocmDateToCal(inputDate).getTime();
        long l = inday.getTime();
        long rol = l - days * 24 * 60 * 60 * 1000;
        java.util.Date rolDay = new java.util.Date(rol);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rolDay);
        int i = calendar.get(Calendar.YEAR);
        int j = calendar.get(Calendar.MONTH) + 1;
        int k = calendar.get(Calendar.DATE);
        return "" + i + (j >= 10 ? "" + j : "0" + j) + (k >= 10 ? "" + k : "0" + k);
    }

    public static Date rolDate(Date inputDate, long days) {
        SimpleDateFormat DATE_FORMATE = new SimpleDateFormat("yyyyMMdd");
        String ret = rolDate(DATE_FORMATE.format(inputDate), days);
        return new Date(bocmDateToCal(ret).getTimeInMillis());

    }

    public static Calendar bocmDateToCal(String s) {
        int i = Integer.parseInt(s.substring(0, 4));
        int j = Integer.parseInt(s.substring(4, 6)) - 1;
        int k = Integer.parseInt(s.substring(6, 8));
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(i, j, k, 0, 0, 0);
        return calendar;
    }

    // ******************************************************************************//
    // ******************************************************************************//

    private static final String FORMATTYPE1 = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMATTYPE2 = "yyyy/MM/dd HH:mm:ss";
    private static final String FORMATTYPE3 = "yyyy年MM月dd日 HH:mm:ss";
    private static final String FORMATTYPE4 = "yyyy年MM月dd日 HH时mm分ss秒";
    private static final String FORMATTYPE5 = "yyyy年MM月dd日 E HH:mm:ss";
    private static final String FORMATTYPE6 = "yyyy年MM月dd日 E HH时mm分ss秒";
    private static final String FORMATTYPE7 = "yyyyMMdd";
    private static final String FORMATTYPE8 = "yyyyMMddHHmmss";
    private static final String FORMATTYPE9 = "yyyy-MM-dd";
    private static final String FORMATTYPE10 = "yyyy_MM";
    private static final String FORMATTYPE11 = "yyyy";
    private static final String FORMATTYPE12 = "yyyyMM";
    private static final String FORMATTYPE13 = "yyyy-MM";
    private static final String FORMATTYPE14 = "yyyy年MM月dd日 HH:mm";

    /**
     * 修改日期
     * 
     * @param Date (待修改日期)
     * @param int (修改的位置，分别表示年月日时分秒)
     * @param int (修改量大小，可正可负)
     * @return Date
     */
    private static Date alterDate(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();

    }

    /**
     * 修改年
     * 
     * @param Date
     * @param int 正负决定增或减
     * @return Date
     */
    public static Date alterYear(Date date, int amount) {
        return alterDate(date, Calendar.YEAR, amount);
    }

    /**
     * 修改月
     * 
     * @param Date
     * @param int 正负决定增或减
     * @return Date
     */
    public static Date alterMonty(Date date, int amount) {
        return alterDate(date, Calendar.MONTH, amount);
    }

    /**
     * 修改日
     * 
     * @param Date
     * @param int 正负决定增或减
     * @return Date
     */
    public static Date alterDay(Date date, int amount) {
        return alterDate(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 修改时
     * 
     * @param Date
     * @param int 正负决定增或减
     * @return Date
     */
    public static Date alterHour(Date date, int amount) {
        return alterDate(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 修改分
     * 
     * @param Date
     * @param int 正负决定增或减
     * @return Date
     */
    public static Date alterMinute(Date date, int amount) {
        return alterDate(date, Calendar.MINUTE, amount);
    }

    /**
     * 修改秒
     * 
     * @param Date
     * @param int 正负决定增或减
     * @return Date
     */
    public static Date alterSecond(Date date, int amount) {
        return alterDate(date, Calendar.SECOND, amount);
    }

    /**
     * 根据传入的日期，默认返回当天零点时间戳
     * 
     * @param String
     * @return Date
     * @throws ParseException
     */
    public static Date getStartTime(String date) throws ParseException {
        if (StringUtil.isEmpty(date)) {
            return null;
        }
        String start = date + "000000";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setLenient(false);
        return sdf.parse(start);
    }

    public static Date getStartTimeOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    // ----------------------------------------------------------------
    // Insurance Timestamp
    public static Date convert2StartDate(Date source) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date convert2EndDate(Date source) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 展示给用户的保障开始时间, 即当日凌晨.
     * 
     * @param source Date
     * @return 2008-05-02 00:00:00
     */
    public static String getPolicyStartTime(Date source) {
//        return get(1, convert2StartDate(source));
        return get(1, source);
    }

    /**
     * 展示给用户的保障结束时间, 即当前日期的24点.
     * 
     * @param source Date
     * @return 2008-05-02 24:00:00
     * @see DateUtil#getPolicyStartTime(Date)
     */
    public static String getPolicyEndTime(Date source) {
        return get(9, source) + " 24:00:00";
    }

    /**
     * 用于网络传输的保障开始时间, 即当日凌晨.
     * 
     * @param source Date
     * @return 200805021000000
     */
    public static String getTransferedPolicyStartTime(Date source) {
        return get(8, convert2StartDate(source));
    }

    /**
     * 用于网络传输的保障结束时间, 时间为次日凌晨.
     * 
     * @param source Date
     * @return 200805022000000
     * @see DateUtil#getTransferedPolicyStartTime(Date)
     */
    public static String getTransferedPolicyEndTime(Date source) {
        return get(8, alterSecond(source, 1));
    }

    public static String getStartTime(Date source) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        return get(1, date);
    }

    public static String getStartTime(int format, Date source) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        return get(format, date);
    }

    public static String getEndTime(Date source) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date date = calendar.getTime();
        return get(1, date);
    }

    /**
     * 根据传入的日期，默认返回当天最后时间戳
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getEndTime(String date) throws ParseException {
        String end = date + "235959";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setLenient(false);
        return sdf.parse(end);
    }

    /**
     * 取得系统当前日期sqlDate
     * 
     * @param
     * @return java.sql.Date
     */
    public static java.sql.Date getCurrDate() {
        return new java.sql.Date(System.currentTimeMillis());

    }

    /**
     * 获取当天零点时间yyyy-mm-dd hh-mm-ss-ms
     * 
     * @return Date
     */
    public static Date getTodayZeroDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前时间yyyyMMddHHmmss
     * 
     * @return String
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat DATE_FORMATE = new SimpleDateFormat("yyyyMMddHHmmss");
        return DATE_FORMATE.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 返回：当前系统年份
     * 
     * @return String
     */
    public static String getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(new java.util.Date()).split("-")[0];
    }

    /**
     * 返回：当前系统月份
     * 
     * @return 09
     */
    public static String getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(new java.util.Date()).split("-")[1];
    }

    /**
     * 返回：当前系统日
     * 
     * @return 09
     */
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(new java.util.Date()).split("-")[2].split(" ")[0];
    }

    /**
     * 返回：系统当前日期
     * 
     * @return 2009-09-09
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(new java.util.Date()).split(" ")[0];
    }

    /**
     * 返回：系统当前时间
     * 
     * @return 09:09:09
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(new java.util.Date()).split(" ")[1];
    }

    /**
     * 返回：系统当前完整日期时间
     * 
     * @return 2009-09-09 09:09:09
     */
    public static String getCurrentFullTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(new java.util.Date());
    }

    /**
     * 返回：系统当前时间时
     * 
     * @return 09
     */
    public static String getHours() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(new java.util.Date()).split(" ")[1].split(":")[0];
    }

    /**
     * 返回：系统当前时间分
     * 
     * @return 12
     */
    public static String getMinutes() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(new java.util.Date()).split(" ")[1].split(":")[1];
    }

    /**
     * 返回：系统当前时间秒
     * 
     * @return 12
     */
    public static String getSeconds() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.format(new java.util.Date()).split(" ")[1].split(":")[2];
    }

    public static Date stringToDate(String str, String format) {
        if (StringUtil.isEmpty1(str) || StringUtil.isEmpty1(format)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            return sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date stringToDate1(String str) {
        if (StringUtil.isEmpty1(str)) {
            return null;
        }
        return stringToDate(str, FORMATTYPE9);
    }

    public static Date stringToDateForQuery(String str) {
        if (StringUtil.isEmpty1(str) || str.length() != 14) {
            return null;
        }
        return stringToDate(str, FORMATTYPE8);
    }

    public static Date stringToDateCompareNow(String str, long time) {
        if (StringUtil.isEmpty1(str) || time < 0) {
            return null;
        }
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATTYPE8);
        sdf.setLenient(false);
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }

        long timediff = date.getTime() - new Date().getTime();
        if (timediff > time || timediff < -time) {
            return null;
        }
        return date;
    }

    public static Date stringToDateCompareNow(String str) {
        if (StringUtil.isEmpty1(str)) {
            return null;
        }
        return stringToDateCompareNow(str, 43200000);
    }

    public static Date stringToDate(int formattype, String str) {
        if (StringUtil.isEmpty1(str)) {
            return null;
        }
        String type = "";
        switch (formattype) {
        case 1:
            type = FORMATTYPE1;
            break;
        case 2:
            type = FORMATTYPE2;
            break;
        case 3:
            type = FORMATTYPE3;
            break;
        case 4:
            type = FORMATTYPE4;
            break;
        case 5:
            type = FORMATTYPE5;
            break;
        case 6:
            type = FORMATTYPE6;
            break;
        case 7:
            type = FORMATTYPE7;
            break;
        case 8:
            type = FORMATTYPE8;
            break;
        case 9:
            type = FORMATTYPE9;
            break;
        case 10:
            type = FORMATTYPE10;
            break;
        case 11:
            type = FORMATTYPE11;
            break;
        case 12:
            type = FORMATTYPE12;
            break;
        case 13:
            type = FORMATTYPE13;
            break;
        default:
            type = FORMATTYPE8;
            break;
        }
        return stringToDate(str, type);
    }

    public static String stringToTimestamp(String strtime) {
        if (StringUtil.isEmpty1(strtime)) {
            return "";
        }
        return strtime.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
    }

    public static String dateToString(Date date, String format) {
        if (date == null || StringUtil.isEmpty1(format)) {
            return "";
        }
        String retstr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            retstr = sdf.format(date);
        } catch (Exception e) {
            return "";
        }
        return retstr;
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        return dateToString(date, FORMATTYPE8);
    }

    public static String dateToString(Object obj, String format) {
        if (StringUtil.isEmpyt1(obj) || StringUtil.isEmpty1(format)) {
            return "";
        }
        String retstr = "";
        try {
            Date date = (Date) obj;
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            retstr = sdf.format(date);
        } catch (Exception e) {
            return "";
        }
        return retstr;
    }

    public static String dateToString1(Object obj) {
        if (StringUtil.isEmpyt1(obj)) {
            return "";
        }
        return dateToString(obj, FORMATTYPE7);
    }

    public static Long dateToSeconds(String datestr, String format) {
        if (StringUtil.isEmpty1(datestr) || StringUtil.isEmpty1(format)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(datestr);
        } catch (ParseException e) {
            return null;
        }
        if (date != null) {
            Long ret = date.getTime();
            return ret / 1000;
        }
        return 0L;
    }

    public static String timestampToString(String timestamp) {
        if (StringUtil.isEmpty1(timestamp) || !StringUtil.isNumeric(timestamp)) {
            return "";
        }
        String str = timestamp.replaceAll(" ", "");
        StringBuffer stringBuffer = new StringBuffer();
        int length = str.length();
        if (length == 8) {
            stringBuffer.append(str.substring(0, 4));
            stringBuffer.append("-");
            stringBuffer.append(str.substring(4, 6));
            stringBuffer.append("-");
            stringBuffer.append(str.substring(6, 8));
            return stringBuffer.toString();
        } else if (length == 14) {
            stringBuffer.append(str.substring(0, 4));
            stringBuffer.append("-");
            stringBuffer.append(str.substring(4, 6));
            stringBuffer.append("-");
            stringBuffer.append(str.substring(6, 8));
            stringBuffer.append(" ");
            stringBuffer.append(str.substring(8, 10));
            stringBuffer.append(":");
            stringBuffer.append(str.substring(10, 12));
            stringBuffer.append(":");
            stringBuffer.append(str.substring(12, 14));
            return stringBuffer.toString();
        } else {
            return "";
        }
    }

    public static String convertToShowTime1(String str, String format) {
        if (StringUtil.isEmpty1(str) || StringUtil.isEmpty1(format)) {
            return "";
        }
        String retstr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMATTYPE8);
            sdf.setLenient(false);
            Date date = sdf.parse(str);
            sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            retstr = sdf.format(date);
        } catch (Exception e) {
            return "";
        }
        return retstr;
    }

    public static String convertToShowTime1(String str) {
        if (StringUtil.isEmpty1(str)) {
            return "";
        }
        return convertToShowTime1(str, FORMATTYPE14);
    }

    public static String convertToShowTime1(Date date, String format) {
        if (date == null || StringUtil.isEmpty1(format)) {
            return "";
        }
        String retstr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            retstr = sdf.format(date);
        } catch (Exception e) {
            return "";
        }
        return retstr;
    }

    public static String convertToShowTime1(Date date) {
        if (date == null) {
            return "";
        }
        return convertToShowTime1(date, FORMATTYPE14);
    }

    public static Map<String, String> dateStrToMap(String date, String beginTime, String endTime) {
        if (StringUtil.isEmpty1(date) || date.length() < 8) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(date.substring(0, 4));
        stringBuffer.append("-");
        stringBuffer.append(date.substring(4, 6));
        stringBuffer.append("-");
        stringBuffer.append(date.substring(6, 8));
        Map<String, String> map = new HashMap<String, String>();
        map.put(beginTime, stringBuffer.toString().trim() + " 00:00:00:00");
        map.put(endTime, stringBuffer.toString().trim() + " 23:59:59:58");
        return map;
    }

    /**
     * 计算日期相差月份
     * 
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDiffMonth(Date beginDate, Date endDate) {
        Calendar calbegin = Calendar.getInstance();
        Calendar calend = Calendar.getInstance();
        calbegin.setTime(beginDate);
        calend.setTime(endDate);
        int m_begin = calbegin.get(Calendar.MONTH) + 1; // 获得开始日期月份
        int m_end = calend.get(Calendar.MONTH) + 1; // 获得结束日期月份
        // 获得结束日期于开始的相差月份
        int checkmonth = m_end - m_begin + (calend.get(Calendar.YEAR) - calbegin.get(Calendar.YEAR)) * 12;
        return checkmonth;
    }

    /**
     * Get different day between BeginDate and EndDate.
     * 
     * @param beginDate begin date
     * @param endDate end date
     * @return days
     */
    public static int getDiffDay(Date beginDate, Date endDate) {
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000));
        if (days <= 0)
            return 1;
        return days;
    }

    public static int getDiffYear(Date beginDate, Date endDate) {
        // now
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);

        if (cal.before(endDate)) {
            return 0;
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(endDate);

        int yearOfEndDate = cal.get(Calendar.YEAR);
        int monthOfEndDate = cal.get(Calendar.MONTH);
        int dayOfEndDate = cal.get(Calendar.DAY_OF_MONTH);

        int diffYear = yearNow - yearOfEndDate;

        if (monthNow <= monthOfEndDate) {
            if (monthNow == monthOfEndDate) {
                if (dayOfMonthNow < dayOfEndDate) {
                    diffYear--;
                }
            } else {
                diffYear--;
            }
        }

        return diffYear;
    }

    public static int getAge(Date birthday) {
        return getDiffYear(new Date(), birthday);
    }

    public static int getDiffMins(Date beginDate, Date endDate) {
        return (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 60));
    }

    public static boolean isOneDay(Date one, Date other) {
        return get(7, one).equals(get(7, other));
    }

    /**
     * 比较两个日期是否相等，精确到秒.
     * 
     * @param one
     * @param other
     * @return
     */
    public static boolean isSameTime(Date one, Date other) {
        return get(1, one).equals(get(1, other));
    }

    /**
     * 计算保险止期.
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date compactInsuranceEndDate(Date beginDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int endDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        Calendar calendar0 = Calendar.getInstance();
        calendar0.setTime(beginDate);
        int startDay = calendar0.get(Calendar.DAY_OF_MONTH);
        if (startDay <= endDay) {
            return DateUtil.alterDay(endDate, -1);
        }
        return endDate;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
}
