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
public class SlotPart implements org.apache.thrift.TBase<SlotPart, SlotPart._Fields>, java.io.Serializable, Cloneable, Comparable<SlotPart> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SlotPart");

  private static final org.apache.thrift.protocol.TField INSTANCE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("instanceId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SLOT_PART_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("slotPartId", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SlotPartStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SlotPartTupleSchemeFactory());
  }

  public long instanceId; // required
  public long slotPartId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    INSTANCE_ID((short)1, "instanceId"),
    SLOT_PART_ID((short)2, "slotPartId");

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
        case 1: // INSTANCE_ID
          return INSTANCE_ID;
        case 2: // SLOT_PART_ID
          return SLOT_PART_ID;
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
  private static final int __INSTANCEID_ISSET_ID = 0;
  private static final int __SLOTPARTID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.INSTANCE_ID, new org.apache.thrift.meta_data.FieldMetaData("instanceId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SLOT_PART_ID, new org.apache.thrift.meta_data.FieldMetaData("slotPartId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SlotPart.class, metaDataMap);
  }

  public SlotPart() {
  }

  public SlotPart(
    long instanceId,
    long slotPartId)
  {
    this();
    this.instanceId = instanceId;
    setInstanceIdIsSet(true);
    this.slotPartId = slotPartId;
    setSlotPartIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SlotPart(SlotPart other) {
    __isset_bitfield = other.__isset_bitfield;
    this.instanceId = other.instanceId;
    this.slotPartId = other.slotPartId;
  }

  public SlotPart deepCopy() {
    return new SlotPart(this);
  }

  @Override
  public void clear() {
    setInstanceIdIsSet(false);
    this.instanceId = 0;
    setSlotPartIdIsSet(false);
    this.slotPartId = 0;
  }

  public long getInstanceId() {
    return this.instanceId;
  }

  public SlotPart setInstanceId(long instanceId) {
    this.instanceId = instanceId;
    setInstanceIdIsSet(true);
    return this;
  }

  public void unsetInstanceId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __INSTANCEID_ISSET_ID);
  }

  /** Returns true if field instanceId is set (has been assigned a value) and false otherwise */
  public boolean isSetInstanceId() {
    return EncodingUtils.testBit(__isset_bitfield, __INSTANCEID_ISSET_ID);
  }

  public void setInstanceIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __INSTANCEID_ISSET_ID, value);
  }

  public long getSlotPartId() {
    return this.slotPartId;
  }

  public SlotPart setSlotPartId(long slotPartId) {
    this.slotPartId = slotPartId;
    setSlotPartIdIsSet(true);
    return this;
  }

  public void unsetSlotPartId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SLOTPARTID_ISSET_ID);
  }

  /** Returns true if field slotPartId is set (has been assigned a value) and false otherwise */
  public boolean isSetSlotPartId() {
    return EncodingUtils.testBit(__isset_bitfield, __SLOTPARTID_ISSET_ID);
  }

  public void setSlotPartIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SLOTPARTID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case INSTANCE_ID:
      if (value == null) {
        unsetInstanceId();
      } else {
        setInstanceId((Long)value);
      }
      break;

    case SLOT_PART_ID:
      if (value == null) {
        unsetSlotPartId();
      } else {
        setSlotPartId((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case INSTANCE_ID:
      return getInstanceId();

    case SLOT_PART_ID:
      return getSlotPartId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case INSTANCE_ID:
      return isSetInstanceId();
    case SLOT_PART_ID:
      return isSetSlotPartId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SlotPart)
      return this.equals((SlotPart)that);
    return false;
  }

  public boolean equals(SlotPart that) {
    if (that == null)
      return false;

    boolean this_present_instanceId = true;
    boolean that_present_instanceId = true;
    if (this_present_instanceId || that_present_instanceId) {
      if (!(this_present_instanceId && that_present_instanceId))
        return false;
      if (this.instanceId != that.instanceId)
        return false;
    }

    boolean this_present_slotPartId = true;
    boolean that_present_slotPartId = true;
    if (this_present_slotPartId || that_present_slotPartId) {
      if (!(this_present_slotPartId && that_present_slotPartId))
        return false;
      if (this.slotPartId != that.slotPartId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_instanceId = true;
    list.add(present_instanceId);
    if (present_instanceId)
      list.add(instanceId);

    boolean present_slotPartId = true;
    list.add(present_slotPartId);
    if (present_slotPartId)
      list.add(slotPartId);

    return list.hashCode();
  }

  @Override
  public int compareTo(SlotPart other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetInstanceId()).compareTo(other.isSetInstanceId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInstanceId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.instanceId, other.instanceId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSlotPartId()).compareTo(other.isSetSlotPartId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSlotPartId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.slotPartId, other.slotPartId);
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
    StringBuilder sb = new StringBuilder("SlotPart(");
    boolean first = true;

    sb.append("instanceId:");
    sb.append(this.instanceId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("slotPartId:");
    sb.append(this.slotPartId);
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

  private static class SlotPartStandardSchemeFactory implements SchemeFactory {
    public SlotPartStandardScheme getScheme() {
      return new SlotPartStandardScheme();
    }
  }

  private static class SlotPartStandardScheme extends StandardScheme<SlotPart> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SlotPart struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // INSTANCE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.instanceId = iprot.readI64();
              struct.setInstanceIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SLOT_PART_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.slotPartId = iprot.readI64();
              struct.setSlotPartIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SlotPart struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(INSTANCE_ID_FIELD_DESC);
      oprot.writeI64(struct.instanceId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SLOT_PART_ID_FIELD_DESC);
      oprot.writeI64(struct.slotPartId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SlotPartTupleSchemeFactory implements SchemeFactory {
    public SlotPartTupleScheme getScheme() {
      return new SlotPartTupleScheme();
    }
  }

  private static class SlotPartTupleScheme extends TupleScheme<SlotPart> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SlotPart struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetInstanceId()) {
        optionals.set(0);
      }
      if (struct.isSetSlotPartId()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetInstanceId()) {
        oprot.writeI64(struct.instanceId);
      }
      if (struct.isSetSlotPartId()) {
        oprot.writeI64(struct.slotPartId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SlotPart struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.instanceId = iprot.readI64();
        struct.setInstanceIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.slotPartId = iprot.readI64();
        struct.setSlotPartIdIsSet(true);
      }
    }
  }

}

