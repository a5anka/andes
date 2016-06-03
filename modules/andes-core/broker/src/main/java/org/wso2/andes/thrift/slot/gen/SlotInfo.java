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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-06-01")
public class SlotInfo implements org.apache.thrift.TBase<SlotInfo, SlotInfo._Fields>, java.io.Serializable, Cloneable, Comparable<SlotInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SlotInfo");

  private static final org.apache.thrift.protocol.TField MESSAGE_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("messageCount", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField START_MESSAGE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("startMessageId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField END_MESSAGE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("endMessageId", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField QUEUE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("queueName", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ASSIGNED_NODE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("assignedNodeId", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField HAS_OVERLAPPING_SLOTS_FIELD_DESC = new org.apache.thrift.protocol.TField("hasOverlappingSlots", org.apache.thrift.protocol.TType.BOOL, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SlotInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SlotInfoTupleSchemeFactory());
  }

  public long messageCount; // optional
  public long startMessageId; // required
  public long endMessageId; // required
  public String queueName; // required
  public String assignedNodeId; // required
  public boolean hasOverlappingSlots; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MESSAGE_COUNT((short)1, "messageCount"),
    START_MESSAGE_ID((short)2, "startMessageId"),
    END_MESSAGE_ID((short)3, "endMessageId"),
    QUEUE_NAME((short)4, "queueName"),
    ASSIGNED_NODE_ID((short)5, "assignedNodeId"),
    HAS_OVERLAPPING_SLOTS((short)6, "hasOverlappingSlots");

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
        case 1: // MESSAGE_COUNT
          return MESSAGE_COUNT;
        case 2: // START_MESSAGE_ID
          return START_MESSAGE_ID;
        case 3: // END_MESSAGE_ID
          return END_MESSAGE_ID;
        case 4: // QUEUE_NAME
          return QUEUE_NAME;
        case 5: // ASSIGNED_NODE_ID
          return ASSIGNED_NODE_ID;
        case 6: // HAS_OVERLAPPING_SLOTS
          return HAS_OVERLAPPING_SLOTS;
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
  private static final int __MESSAGECOUNT_ISSET_ID = 0;
  private static final int __STARTMESSAGEID_ISSET_ID = 1;
  private static final int __ENDMESSAGEID_ISSET_ID = 2;
  private static final int __HASOVERLAPPINGSLOTS_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.MESSAGE_COUNT};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MESSAGE_COUNT, new org.apache.thrift.meta_data.FieldMetaData("messageCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.START_MESSAGE_ID, new org.apache.thrift.meta_data.FieldMetaData("startMessageId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.END_MESSAGE_ID, new org.apache.thrift.meta_data.FieldMetaData("endMessageId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.QUEUE_NAME, new org.apache.thrift.meta_data.FieldMetaData("queueName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ASSIGNED_NODE_ID, new org.apache.thrift.meta_data.FieldMetaData("assignedNodeId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.HAS_OVERLAPPING_SLOTS, new org.apache.thrift.meta_data.FieldMetaData("hasOverlappingSlots", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SlotInfo.class, metaDataMap);
  }

  public SlotInfo() {
  }

  public SlotInfo(
    long startMessageId,
    long endMessageId,
    String queueName,
    String assignedNodeId,
    boolean hasOverlappingSlots)
  {
    this();
    this.startMessageId = startMessageId;
    setStartMessageIdIsSet(true);
    this.endMessageId = endMessageId;
    setEndMessageIdIsSet(true);
    this.queueName = queueName;
    this.assignedNodeId = assignedNodeId;
    this.hasOverlappingSlots = hasOverlappingSlots;
    setHasOverlappingSlotsIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SlotInfo(SlotInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.messageCount = other.messageCount;
    this.startMessageId = other.startMessageId;
    this.endMessageId = other.endMessageId;
    if (other.isSetQueueName()) {
      this.queueName = other.queueName;
    }
    if (other.isSetAssignedNodeId()) {
      this.assignedNodeId = other.assignedNodeId;
    }
    this.hasOverlappingSlots = other.hasOverlappingSlots;
  }

  public SlotInfo deepCopy() {
    return new SlotInfo(this);
  }

  @Override
  public void clear() {
    setMessageCountIsSet(false);
    this.messageCount = 0;
    setStartMessageIdIsSet(false);
    this.startMessageId = 0;
    setEndMessageIdIsSet(false);
    this.endMessageId = 0;
    this.queueName = null;
    this.assignedNodeId = null;
    setHasOverlappingSlotsIsSet(false);
    this.hasOverlappingSlots = false;
  }

  public long getMessageCount() {
    return this.messageCount;
  }

  public SlotInfo setMessageCount(long messageCount) {
    this.messageCount = messageCount;
    setMessageCountIsSet(true);
    return this;
  }

  public void unsetMessageCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MESSAGECOUNT_ISSET_ID);
  }

  /** Returns true if field messageCount is set (has been assigned a value) and false otherwise */
  public boolean isSetMessageCount() {
    return EncodingUtils.testBit(__isset_bitfield, __MESSAGECOUNT_ISSET_ID);
  }

  public void setMessageCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MESSAGECOUNT_ISSET_ID, value);
  }

  public long getStartMessageId() {
    return this.startMessageId;
  }

  public SlotInfo setStartMessageId(long startMessageId) {
    this.startMessageId = startMessageId;
    setStartMessageIdIsSet(true);
    return this;
  }

  public void unsetStartMessageId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STARTMESSAGEID_ISSET_ID);
  }

  /** Returns true if field startMessageId is set (has been assigned a value) and false otherwise */
  public boolean isSetStartMessageId() {
    return EncodingUtils.testBit(__isset_bitfield, __STARTMESSAGEID_ISSET_ID);
  }

  public void setStartMessageIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STARTMESSAGEID_ISSET_ID, value);
  }

  public long getEndMessageId() {
    return this.endMessageId;
  }

  public SlotInfo setEndMessageId(long endMessageId) {
    this.endMessageId = endMessageId;
    setEndMessageIdIsSet(true);
    return this;
  }

  public void unsetEndMessageId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENDMESSAGEID_ISSET_ID);
  }

  /** Returns true if field endMessageId is set (has been assigned a value) and false otherwise */
  public boolean isSetEndMessageId() {
    return EncodingUtils.testBit(__isset_bitfield, __ENDMESSAGEID_ISSET_ID);
  }

  public void setEndMessageIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENDMESSAGEID_ISSET_ID, value);
  }

  public String getQueueName() {
    return this.queueName;
  }

  public SlotInfo setQueueName(String queueName) {
    this.queueName = queueName;
    return this;
  }

  public void unsetQueueName() {
    this.queueName = null;
  }

  /** Returns true if field queueName is set (has been assigned a value) and false otherwise */
  public boolean isSetQueueName() {
    return this.queueName != null;
  }

  public void setQueueNameIsSet(boolean value) {
    if (!value) {
      this.queueName = null;
    }
  }

  public String getAssignedNodeId() {
    return this.assignedNodeId;
  }

  public SlotInfo setAssignedNodeId(String assignedNodeId) {
    this.assignedNodeId = assignedNodeId;
    return this;
  }

  public void unsetAssignedNodeId() {
    this.assignedNodeId = null;
  }

  /** Returns true if field assignedNodeId is set (has been assigned a value) and false otherwise */
  public boolean isSetAssignedNodeId() {
    return this.assignedNodeId != null;
  }

  public void setAssignedNodeIdIsSet(boolean value) {
    if (!value) {
      this.assignedNodeId = null;
    }
  }

  public boolean isHasOverlappingSlots() {
    return this.hasOverlappingSlots;
  }

  public SlotInfo setHasOverlappingSlots(boolean hasOverlappingSlots) {
    this.hasOverlappingSlots = hasOverlappingSlots;
    setHasOverlappingSlotsIsSet(true);
    return this;
  }

  public void unsetHasOverlappingSlots() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __HASOVERLAPPINGSLOTS_ISSET_ID);
  }

  /** Returns true if field hasOverlappingSlots is set (has been assigned a value) and false otherwise */
  public boolean isSetHasOverlappingSlots() {
    return EncodingUtils.testBit(__isset_bitfield, __HASOVERLAPPINGSLOTS_ISSET_ID);
  }

  public void setHasOverlappingSlotsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __HASOVERLAPPINGSLOTS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MESSAGE_COUNT:
      if (value == null) {
        unsetMessageCount();
      } else {
        setMessageCount((Long)value);
      }
      break;

    case START_MESSAGE_ID:
      if (value == null) {
        unsetStartMessageId();
      } else {
        setStartMessageId((Long)value);
      }
      break;

    case END_MESSAGE_ID:
      if (value == null) {
        unsetEndMessageId();
      } else {
        setEndMessageId((Long)value);
      }
      break;

    case QUEUE_NAME:
      if (value == null) {
        unsetQueueName();
      } else {
        setQueueName((String)value);
      }
      break;

    case ASSIGNED_NODE_ID:
      if (value == null) {
        unsetAssignedNodeId();
      } else {
        setAssignedNodeId((String)value);
      }
      break;

    case HAS_OVERLAPPING_SLOTS:
      if (value == null) {
        unsetHasOverlappingSlots();
      } else {
        setHasOverlappingSlots((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MESSAGE_COUNT:
      return getMessageCount();

    case START_MESSAGE_ID:
      return getStartMessageId();

    case END_MESSAGE_ID:
      return getEndMessageId();

    case QUEUE_NAME:
      return getQueueName();

    case ASSIGNED_NODE_ID:
      return getAssignedNodeId();

    case HAS_OVERLAPPING_SLOTS:
      return isHasOverlappingSlots();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MESSAGE_COUNT:
      return isSetMessageCount();
    case START_MESSAGE_ID:
      return isSetStartMessageId();
    case END_MESSAGE_ID:
      return isSetEndMessageId();
    case QUEUE_NAME:
      return isSetQueueName();
    case ASSIGNED_NODE_ID:
      return isSetAssignedNodeId();
    case HAS_OVERLAPPING_SLOTS:
      return isSetHasOverlappingSlots();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SlotInfo)
      return this.equals((SlotInfo)that);
    return false;
  }

  public boolean equals(SlotInfo that) {
    if (that == null)
      return false;

    boolean this_present_messageCount = true && this.isSetMessageCount();
    boolean that_present_messageCount = true && that.isSetMessageCount();
    if (this_present_messageCount || that_present_messageCount) {
      if (!(this_present_messageCount && that_present_messageCount))
        return false;
      if (this.messageCount != that.messageCount)
        return false;
    }

    boolean this_present_startMessageId = true;
    boolean that_present_startMessageId = true;
    if (this_present_startMessageId || that_present_startMessageId) {
      if (!(this_present_startMessageId && that_present_startMessageId))
        return false;
      if (this.startMessageId != that.startMessageId)
        return false;
    }

    boolean this_present_endMessageId = true;
    boolean that_present_endMessageId = true;
    if (this_present_endMessageId || that_present_endMessageId) {
      if (!(this_present_endMessageId && that_present_endMessageId))
        return false;
      if (this.endMessageId != that.endMessageId)
        return false;
    }

    boolean this_present_queueName = true && this.isSetQueueName();
    boolean that_present_queueName = true && that.isSetQueueName();
    if (this_present_queueName || that_present_queueName) {
      if (!(this_present_queueName && that_present_queueName))
        return false;
      if (!this.queueName.equals(that.queueName))
        return false;
    }

    boolean this_present_assignedNodeId = true && this.isSetAssignedNodeId();
    boolean that_present_assignedNodeId = true && that.isSetAssignedNodeId();
    if (this_present_assignedNodeId || that_present_assignedNodeId) {
      if (!(this_present_assignedNodeId && that_present_assignedNodeId))
        return false;
      if (!this.assignedNodeId.equals(that.assignedNodeId))
        return false;
    }

    boolean this_present_hasOverlappingSlots = true;
    boolean that_present_hasOverlappingSlots = true;
    if (this_present_hasOverlappingSlots || that_present_hasOverlappingSlots) {
      if (!(this_present_hasOverlappingSlots && that_present_hasOverlappingSlots))
        return false;
      if (this.hasOverlappingSlots != that.hasOverlappingSlots)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_messageCount = true && (isSetMessageCount());
    list.add(present_messageCount);
    if (present_messageCount)
      list.add(messageCount);

    boolean present_startMessageId = true;
    list.add(present_startMessageId);
    if (present_startMessageId)
      list.add(startMessageId);

    boolean present_endMessageId = true;
    list.add(present_endMessageId);
    if (present_endMessageId)
      list.add(endMessageId);

    boolean present_queueName = true && (isSetQueueName());
    list.add(present_queueName);
    if (present_queueName)
      list.add(queueName);

    boolean present_assignedNodeId = true && (isSetAssignedNodeId());
    list.add(present_assignedNodeId);
    if (present_assignedNodeId)
      list.add(assignedNodeId);

    boolean present_hasOverlappingSlots = true;
    list.add(present_hasOverlappingSlots);
    if (present_hasOverlappingSlots)
      list.add(hasOverlappingSlots);

    return list.hashCode();
  }

  @Override
  public int compareTo(SlotInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMessageCount()).compareTo(other.isSetMessageCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessageCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messageCount, other.messageCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStartMessageId()).compareTo(other.isSetStartMessageId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartMessageId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startMessageId, other.startMessageId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndMessageId()).compareTo(other.isSetEndMessageId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndMessageId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endMessageId, other.endMessageId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetQueueName()).compareTo(other.isSetQueueName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQueueName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.queueName, other.queueName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAssignedNodeId()).compareTo(other.isSetAssignedNodeId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAssignedNodeId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.assignedNodeId, other.assignedNodeId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHasOverlappingSlots()).compareTo(other.isSetHasOverlappingSlots());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHasOverlappingSlots()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hasOverlappingSlots, other.hasOverlappingSlots);
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
    StringBuilder sb = new StringBuilder("SlotInfo(");
    boolean first = true;

    if (isSetMessageCount()) {
      sb.append("messageCount:");
      sb.append(this.messageCount);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("startMessageId:");
    sb.append(this.startMessageId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("endMessageId:");
    sb.append(this.endMessageId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("queueName:");
    if (this.queueName == null) {
      sb.append("null");
    } else {
      sb.append(this.queueName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("assignedNodeId:");
    if (this.assignedNodeId == null) {
      sb.append("null");
    } else {
      sb.append(this.assignedNodeId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("hasOverlappingSlots:");
    sb.append(this.hasOverlappingSlots);
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

  private static class SlotInfoStandardSchemeFactory implements SchemeFactory {
    public SlotInfoStandardScheme getScheme() {
      return new SlotInfoStandardScheme();
    }
  }

  private static class SlotInfoStandardScheme extends StandardScheme<SlotInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SlotInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MESSAGE_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.messageCount = iprot.readI64();
              struct.setMessageCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // START_MESSAGE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.startMessageId = iprot.readI64();
              struct.setStartMessageIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // END_MESSAGE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.endMessageId = iprot.readI64();
              struct.setEndMessageIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // QUEUE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.queueName = iprot.readString();
              struct.setQueueNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ASSIGNED_NODE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.assignedNodeId = iprot.readString();
              struct.setAssignedNodeIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // HAS_OVERLAPPING_SLOTS
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.hasOverlappingSlots = iprot.readBool();
              struct.setHasOverlappingSlotsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SlotInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetMessageCount()) {
        oprot.writeFieldBegin(MESSAGE_COUNT_FIELD_DESC);
        oprot.writeI64(struct.messageCount);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(START_MESSAGE_ID_FIELD_DESC);
      oprot.writeI64(struct.startMessageId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(END_MESSAGE_ID_FIELD_DESC);
      oprot.writeI64(struct.endMessageId);
      oprot.writeFieldEnd();
      if (struct.queueName != null) {
        oprot.writeFieldBegin(QUEUE_NAME_FIELD_DESC);
        oprot.writeString(struct.queueName);
        oprot.writeFieldEnd();
      }
      if (struct.assignedNodeId != null) {
        oprot.writeFieldBegin(ASSIGNED_NODE_ID_FIELD_DESC);
        oprot.writeString(struct.assignedNodeId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(HAS_OVERLAPPING_SLOTS_FIELD_DESC);
      oprot.writeBool(struct.hasOverlappingSlots);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SlotInfoTupleSchemeFactory implements SchemeFactory {
    public SlotInfoTupleScheme getScheme() {
      return new SlotInfoTupleScheme();
    }
  }

  private static class SlotInfoTupleScheme extends TupleScheme<SlotInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SlotInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetMessageCount()) {
        optionals.set(0);
      }
      if (struct.isSetStartMessageId()) {
        optionals.set(1);
      }
      if (struct.isSetEndMessageId()) {
        optionals.set(2);
      }
      if (struct.isSetQueueName()) {
        optionals.set(3);
      }
      if (struct.isSetAssignedNodeId()) {
        optionals.set(4);
      }
      if (struct.isSetHasOverlappingSlots()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetMessageCount()) {
        oprot.writeI64(struct.messageCount);
      }
      if (struct.isSetStartMessageId()) {
        oprot.writeI64(struct.startMessageId);
      }
      if (struct.isSetEndMessageId()) {
        oprot.writeI64(struct.endMessageId);
      }
      if (struct.isSetQueueName()) {
        oprot.writeString(struct.queueName);
      }
      if (struct.isSetAssignedNodeId()) {
        oprot.writeString(struct.assignedNodeId);
      }
      if (struct.isSetHasOverlappingSlots()) {
        oprot.writeBool(struct.hasOverlappingSlots);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SlotInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.messageCount = iprot.readI64();
        struct.setMessageCountIsSet(true);
      }
      if (incoming.get(1)) {
        struct.startMessageId = iprot.readI64();
        struct.setStartMessageIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.endMessageId = iprot.readI64();
        struct.setEndMessageIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.queueName = iprot.readString();
        struct.setQueueNameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.assignedNodeId = iprot.readString();
        struct.setAssignedNodeIdIsSet(true);
      }
      if (incoming.get(5)) {
        struct.hasOverlappingSlots = iprot.readBool();
        struct.setHasOverlappingSlotsIsSet(true);
      }
    }
  }

}

