/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.wso2.andes.thrift.slot.gen;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-06-14")
public class SlotDataHolder implements org.apache.thrift.TBase<SlotDataHolder, SlotDataHolder._Fields>, java.io.Serializable, Cloneable, Comparable<SlotDataHolder> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SlotDataHolder");

  private static final org.apache.thrift.protocol.TField SLOT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("slotId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SLOT_PART_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("slotPartList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SlotDataHolderStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SlotDataHolderTupleSchemeFactory());
  }

  public long slotId; // required
  public List<SlotPart> slotPartList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SLOT_ID((short)1, "slotId"),
    SLOT_PART_LIST((short)2, "slotPartList");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SLOT_ID
          return SLOT_ID;
        case 2: // SLOT_PART_LIST
          return SLOT_PART_LIST;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __SLOTID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SLOT_ID, new org.apache.thrift.meta_data.FieldMetaData("slotId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SLOT_PART_LIST, new org.apache.thrift.meta_data.FieldMetaData("slotPartList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "SlotPart"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SlotDataHolder.class, metaDataMap);
  }

  public SlotDataHolder() {
  }

  public SlotDataHolder(
    long slotId,
    List<SlotPart> slotPartList)
  {
    this();
    this.slotId = slotId;
    setSlotIdIsSet(true);
    this.slotPartList = slotPartList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SlotDataHolder(SlotDataHolder other) {
    __isset_bitfield = other.__isset_bitfield;
    this.slotId = other.slotId;
    if (other.isSetSlotPartList()) {
      List<SlotPart> __this__slotPartList = new ArrayList<SlotPart>(other.slotPartList.size());
      for (SlotPart other_element : other.slotPartList) {
        __this__slotPartList.add(other_element);
      }
      this.slotPartList = __this__slotPartList;
    }
  }

  public SlotDataHolder deepCopy() {
    return new SlotDataHolder(this);
  }

  @Override
  public void clear() {
    setSlotIdIsSet(false);
    this.slotId = 0;
    this.slotPartList = null;
  }

  public long getSlotId() {
    return this.slotId;
  }

  public SlotDataHolder setSlotId(long slotId) {
    this.slotId = slotId;
    setSlotIdIsSet(true);
    return this;
  }

  public void unsetSlotId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SLOTID_ISSET_ID);
  }

  /** Returns true if field slotId is set (has been assigned a value) and false otherwise */
  public boolean isSetSlotId() {
    return EncodingUtils.testBit(__isset_bitfield, __SLOTID_ISSET_ID);
  }

  public void setSlotIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SLOTID_ISSET_ID, value);
  }

  public int getSlotPartListSize() {
    return (this.slotPartList == null) ? 0 : this.slotPartList.size();
  }

  public java.util.Iterator<SlotPart> getSlotPartListIterator() {
    return (this.slotPartList == null) ? null : this.slotPartList.iterator();
  }

  public void addToSlotPartList(SlotPart elem) {
    if (this.slotPartList == null) {
      this.slotPartList = new ArrayList<SlotPart>();
    }
    this.slotPartList.add(elem);
  }

  public List<SlotPart> getSlotPartList() {
    return this.slotPartList;
  }

  public SlotDataHolder setSlotPartList(List<SlotPart> slotPartList) {
    this.slotPartList = slotPartList;
    return this;
  }

  public void unsetSlotPartList() {
    this.slotPartList = null;
  }

  /** Returns true if field slotPartList is set (has been assigned a value) and false otherwise */
  public boolean isSetSlotPartList() {
    return this.slotPartList != null;
  }

  public void setSlotPartListIsSet(boolean value) {
    if (!value) {
      this.slotPartList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SLOT_ID:
      if (value == null) {
        unsetSlotId();
      } else {
        setSlotId((Long)value);
      }
      break;

    case SLOT_PART_LIST:
      if (value == null) {
        unsetSlotPartList();
      } else {
        setSlotPartList((List<SlotPart>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SLOT_ID:
      return getSlotId();

    case SLOT_PART_LIST:
      return getSlotPartList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SLOT_ID:
      return isSetSlotId();
    case SLOT_PART_LIST:
      return isSetSlotPartList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SlotDataHolder)
      return this.equals((SlotDataHolder)that);
    return false;
  }

  public boolean equals(SlotDataHolder that) {
    if (that == null)
      return false;

    boolean this_present_slotId = true;
    boolean that_present_slotId = true;
    if (this_present_slotId || that_present_slotId) {
      if (!(this_present_slotId && that_present_slotId))
        return false;
      if (this.slotId != that.slotId)
        return false;
    }

    boolean this_present_slotPartList = true && this.isSetSlotPartList();
    boolean that_present_slotPartList = true && that.isSetSlotPartList();
    if (this_present_slotPartList || that_present_slotPartList) {
      if (!(this_present_slotPartList && that_present_slotPartList))
        return false;
      if (!this.slotPartList.equals(that.slotPartList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_slotId = true;
    list.add(present_slotId);
    if (present_slotId)
      list.add(slotId);

    boolean present_slotPartList = true && (isSetSlotPartList());
    list.add(present_slotPartList);
    if (present_slotPartList)
      list.add(slotPartList);

    return list.hashCode();
  }

  @Override
  public int compareTo(SlotDataHolder other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSlotId()).compareTo(other.isSetSlotId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSlotId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.slotId, other.slotId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSlotPartList()).compareTo(other.isSetSlotPartList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSlotPartList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.slotPartList, other.slotPartList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SlotDataHolder(");
    boolean first = true;

    sb.append("slotId:");
    sb.append(this.slotId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("slotPartList:");
    if (this.slotPartList == null) {
      sb.append("null");
    } else {
      sb.append(this.slotPartList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class SlotDataHolderStandardSchemeFactory implements SchemeFactory {
    public SlotDataHolderStandardScheme getScheme() {
      return new SlotDataHolderStandardScheme();
    }
  }

  private static class SlotDataHolderStandardScheme extends StandardScheme<SlotDataHolder> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SlotDataHolder struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SLOT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.slotId = iprot.readI64();
              struct.setSlotIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SLOT_PART_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.slotPartList = new ArrayList<SlotPart>(_list0.size);
                SlotPart _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new SlotPart();
                  _elem1.read(iprot);
                  struct.slotPartList.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setSlotPartListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SlotDataHolder struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SLOT_ID_FIELD_DESC);
      oprot.writeI64(struct.slotId);
      oprot.writeFieldEnd();
      if (struct.slotPartList != null) {
        oprot.writeFieldBegin(SLOT_PART_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.slotPartList.size()));
          for (SlotPart _iter3 : struct.slotPartList)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SlotDataHolderTupleSchemeFactory implements SchemeFactory {
    public SlotDataHolderTupleScheme getScheme() {
      return new SlotDataHolderTupleScheme();
    }
  }

  private static class SlotDataHolderTupleScheme extends TupleScheme<SlotDataHolder> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SlotDataHolder struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSlotId()) {
        optionals.set(0);
      }
      if (struct.isSetSlotPartList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSlotId()) {
        oprot.writeI64(struct.slotId);
      }
      if (struct.isSetSlotPartList()) {
        {
          oprot.writeI32(struct.slotPartList.size());
          for (SlotPart _iter4 : struct.slotPartList)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SlotDataHolder struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.slotId = iprot.readI64();
        struct.setSlotIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.slotPartList = new ArrayList<SlotPart>(_list5.size);
          SlotPart _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new SlotPart();
            _elem6.read(iprot);
            struct.slotPartList.add(_elem6);
          }
        }
        struct.setSlotPartListIsSet(true);
      }
    }
  }

}

