package cn.passionshark.project.community.common.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface GenericDao<E extends Entity>
{
  public abstract int add(E paramE);

  public abstract int update(E paramE);

  public abstract int delete(Serializable paramSerializable);

  public abstract int delete(String paramString, Object[] paramArrayOfObject);

  public abstract int delete(E paramE);

  public abstract List<E> getAll();

  public abstract E get(Serializable paramSerializable);

  public abstract List query(String paramString, Object[] paramArrayOfObject);

  public abstract Object queryOne(String paramString, Object[] paramArrayOfObject);

  public abstract int update(String paramString, Object[] paramArrayOfObject);

  public abstract Map getMap(String paramString, Object[] paramArrayOfObject);
}