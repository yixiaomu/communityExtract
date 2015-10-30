package cn.passionshark.project.community.common.persistence;

import java.io.Serializable;

public abstract interface Entity<C extends Serializable> extends Serializable
{
  public abstract C getId();

  public abstract void setId(C paramC);
}