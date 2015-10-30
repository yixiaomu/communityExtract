package cn.passionshark.project.community.core.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import cn.passionshark.project.community.core.enums.StatusEnum;



public class StatusEnumHandler extends BaseTypeHandler<StatusEnum> implements TypeHandler<StatusEnum> {

    public StatusEnum getNullableResult(ResultSet rs, String paramString) throws SQLException {
        Integer code = rs.getInt(paramString);
        return code == null ? null : StatusEnum.toEnum(code);
    }

    public StatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Integer code = rs.getInt(columnIndex);
        return code == null ? null : StatusEnum.toEnum(code);
    }

    public void setNonNullParameter(PreparedStatement ps, int i, StatusEnum parameter, JdbcType jdbcType)
            throws SQLException {
        StatusEnum obj = (StatusEnum) parameter;
        ps.setInt(i, obj.getCode());
    }

    public StatusEnum getNullableResult(CallableStatement cs, int paramString) throws SQLException {
        Integer code = cs.getInt(paramString);
        return code == null ? null : StatusEnum.toEnum(code);
    }
}
