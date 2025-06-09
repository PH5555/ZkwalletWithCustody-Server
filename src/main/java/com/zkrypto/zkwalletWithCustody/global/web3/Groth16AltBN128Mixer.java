package com.zkrypto.zkwalletWithCustody.global.web3;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.StaticArray11;
import org.web3j.abi.datatypes.generated.StaticArray3;
import org.web3j.abi.datatypes.generated.StaticArray6;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.reflection.Parameterized;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class Groth16AltBN128Mixer extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_UPGRADE_INTERFACE_VERSION = "UPGRADE_INTERFACE_VERSION";

    public static final String FUNC__COMPUTEMERKLEPATH = "_computeMerklePath";

    public static final String FUNC__NODES = "_nodes";

    public static final String FUNC_ACCEPTOWNERSHIP = "acceptOwnership";

    public static final String FUNC_CMLIST = "cmList";

    public static final String FUNC_GETAPK = "getAPK";

    public static final String FUNC_GETAPKBYINDEX = "getAPKByIndex";

    public static final String FUNC_GETAPKFROMINDEX = "getAPKfromIndex";

    public static final String FUNC_GETCIPHERTEXT = "getCiphertext";

    public static final String FUNC_GETENALENGTH = "getEnaLength";

    public static final String FUNC_GETMERKLEPATH = "getMerklePath";

    public static final String FUNC_GETROOTTOP = "getRootTop";

    public static final String FUNC_GETUSERPUBLICKEYS = "getUserPublicKeys";

    public static final String FUNC_GETZKTRANSFERFEE = "getZkTransferFee";

    public static final String FUNC_GETZKTRANSFERFEEADDRESS = "getZkTransferFeeAddress";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_ISNULLIFIED = "isNullified";

    public static final String FUNC_NFLIST = "nfList";

    public static final String FUNC_ONERC1155BATCHRECEIVED = "onERC1155BatchReceived";

    public static final String FUNC_ONERC1155RECEIVED = "onERC1155Received";

    public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PENDINGOWNER = "pendingOwner";

    public static final String FUNC_PROXIABLEUUID = "proxiableUUID";

    public static final String FUNC_REGISTERAUDITOR = "registerAuditor";

    public static final String FUNC_REGISTERUSER = "registerUser";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_RTLIST = "rtList";

    public static final String FUNC_SETZKTRANSFERFEE = "setZkTransferFee";

    public static final String FUNC_SETZKWALLETFEERECEIVER = "setZkWalletFeeReceiver";

    public static final String FUNC_TOKENRECEIVED = "tokenReceived";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPGRADETOANDCALL = "upgradeToAndCall";

    public static final String FUNC_VERSION = "version";

    public static final String FUNC_ZKTRANSFER1155 = "zkTransfer1155";

    public static final String FUNC_ZKTRANSFER20 = "zkTransfer20";

    public static final String FUNC_ZKTRANSFER721 = "zkTransfer721";

    public static final Event INITIALIZED_EVENT = new Event("Initialized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
    ;

    public static final Event LOGDEBUG_EVENT = new Event("LogDebug", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event LOGUSERREGISTER_EVENT = new Event("LogUserRegister", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<CurvePoint>() {}));
    ;

    public static final Event LOGZKTRANSFER_EVENT = new Event("LogZkTransfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<StaticArray11<Uint256>>() {}, new TypeReference<Uint256>() {}, new TypeReference<StaticArray6<Uint256>>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERSTARTED_EVENT = new Event("OwnershipTransferStarted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event UPGRADED_EVENT = new Event("Upgraded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected Groth16AltBN128Mixer(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Groth16AltBN128Mixer(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Groth16AltBN128Mixer(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Groth16AltBN128Mixer(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<InitializedEventResponse> getInitializedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INITIALIZED_EVENT, transactionReceipt);
        ArrayList<InitializedEventResponse> responses = new ArrayList<InitializedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InitializedEventResponse typedResponse = new InitializedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static InitializedEventResponse getInitializedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INITIALIZED_EVENT, log);
        InitializedEventResponse typedResponse = new InitializedEventResponse();
        typedResponse.log = log;
        typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<InitializedEventResponse> initializedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getInitializedEventFromLog(log));
    }

    public Flowable<InitializedEventResponse> initializedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INITIALIZED_EVENT));
        return initializedEventFlowable(filter);
    }

    public static List<LogDebugEventResponse> getLogDebugEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOGDEBUG_EVENT, transactionReceipt);
        ArrayList<LogDebugEventResponse> responses = new ArrayList<LogDebugEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogDebugEventResponse typedResponse = new LogDebugEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.message = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LogDebugEventResponse getLogDebugEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOGDEBUG_EVENT, log);
        LogDebugEventResponse typedResponse = new LogDebugEventResponse();
        typedResponse.log = log;
        typedResponse.message = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<LogDebugEventResponse> logDebugEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLogDebugEventFromLog(log));
    }

    public Flowable<LogDebugEventResponse> logDebugEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGDEBUG_EVENT));
        return logDebugEventFlowable(filter);
    }

    public static List<LogUserRegisterEventResponse> getLogUserRegisterEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOGUSERREGISTER_EVENT, transactionReceipt);
        ArrayList<LogUserRegisterEventResponse> responses = new ArrayList<LogUserRegisterEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogUserRegisterEventResponse typedResponse = new LogUserRegisterEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.pkOwn = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.pkEnc = (CurvePoint) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LogUserRegisterEventResponse getLogUserRegisterEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOGUSERREGISTER_EVENT, log);
        LogUserRegisterEventResponse typedResponse = new LogUserRegisterEventResponse();
        typedResponse.log = log;
        typedResponse.addr = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.pkOwn = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.pkEnc = (CurvePoint) eventValues.getNonIndexedValues().get(2);
        return typedResponse;
    }

    public Flowable<LogUserRegisterEventResponse> logUserRegisterEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLogUserRegisterEventFromLog(log));
    }

    public Flowable<LogUserRegisterEventResponse> logUserRegisterEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGUSERREGISTER_EVENT));
        return logUserRegisterEventFlowable(filter);
    }

    public static List<LogZkTransferEventResponse> getLogZkTransferEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOGZKTRANSFER_EVENT, transactionReceipt);
        ArrayList<LogZkTransferEventResponse> responses = new ArrayList<LogZkTransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogZkTransferEventResponse typedResponse = new LogZkTransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.nullifier = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.com = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.ct = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(2)).getNativeValueCopy();
            typedResponse.numLeaves = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.ena = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(4)).getNativeValueCopy();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LogZkTransferEventResponse getLogZkTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOGZKTRANSFER_EVENT, log);
        LogZkTransferEventResponse typedResponse = new LogZkTransferEventResponse();
        typedResponse.log = log;
        typedResponse.nullifier = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.com = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.ct = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(2)).getNativeValueCopy();
        typedResponse.numLeaves = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.ena = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(4)).getNativeValueCopy();
        return typedResponse;
    }

    public Flowable<LogZkTransferEventResponse> logZkTransferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLogZkTransferEventFromLog(log));
    }

    public Flowable<LogZkTransferEventResponse> logZkTransferEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGZKTRANSFER_EVENT));
        return logZkTransferEventFlowable(filter);
    }

    public static List<OwnershipTransferStartedEventResponse> getOwnershipTransferStartedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERSTARTED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferStartedEventResponse> responses = new ArrayList<OwnershipTransferStartedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferStartedEventResponse typedResponse = new OwnershipTransferStartedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferStartedEventResponse getOwnershipTransferStartedEventFromLog(
            Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERSTARTED_EVENT, log);
        OwnershipTransferStartedEventResponse typedResponse = new OwnershipTransferStartedEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferStartedEventResponse> ownershipTransferStartedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferStartedEventFromLog(log));
    }

    public Flowable<OwnershipTransferStartedEventResponse> ownershipTransferStartedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERSTARTED_EVENT));
        return ownershipTransferStartedEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public static List<UpgradedEventResponse> getUpgradedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UPGRADED_EVENT, transactionReceipt);
        ArrayList<UpgradedEventResponse> responses = new ArrayList<UpgradedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpgradedEventResponse typedResponse = new UpgradedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UpgradedEventResponse getUpgradedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(UPGRADED_EVENT, log);
        UpgradedEventResponse typedResponse = new UpgradedEventResponse();
        typedResponse.log = log;
        typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<UpgradedEventResponse> upgradedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUpgradedEventFromLog(log));
    }

    public Flowable<UpgradedEventResponse> upgradedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPGRADED_EVENT));
        return upgradedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> UPGRADE_INTERFACE_VERSION() {
        final Function function = new Function(FUNC_UPGRADE_INTERFACE_VERSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> _computeMerklePath(BigInteger index) {
        final Function function = new Function(FUNC__COMPUTEMERKLEPATH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<byte[]> _nodes(BigInteger param0) {
        final Function function = new Function(FUNC__NODES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> acceptOwnership() {
        final Function function = new Function(
                FUNC_ACCEPTOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> cmList(BigInteger param0) {
        final Function function = new Function(FUNC_CMLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple2<CurvePoint, BigInteger>> getAPK() {
        final Function function = new Function(FUNC_GETAPK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<CurvePoint>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<CurvePoint, BigInteger>>(function,
                new Callable<Tuple2<CurvePoint, BigInteger>>() {
                    @Override
                    public Tuple2<CurvePoint, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<CurvePoint, BigInteger>(
                                (CurvePoint) results.get(0), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<CurvePoint> getAPKByIndex(BigInteger index) {
        final Function function = new Function(FUNC_GETAPKBYINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<CurvePoint>() {}));
        return executeRemoteCallSingleValueReturn(function, CurvePoint.class);
    }

    public RemoteFunctionCall<CurvePoint> getAPKfromIndex(BigInteger index) {
        final Function function = new Function(FUNC_GETAPKFROMINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<CurvePoint>() {}));
        return executeRemoteCallSingleValueReturn(function, CurvePoint.class);
    }

    public RemoteFunctionCall<ENA> getCiphertext(BigInteger addr, BigInteger index) {
        final Function function = new Function(FUNC_GETCIPHERTEXT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(addr), 
                new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<ENA>() {}));
        return executeRemoteCallSingleValueReturn(function, ENA.class);
    }

    public RemoteFunctionCall<BigInteger> getEnaLength(BigInteger addr) {
        final Function function = new Function(FUNC_GETENALENGTH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getMerklePath(BigInteger index) {
        final Function function = new Function(FUNC_GETMERKLEPATH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getRootTop() {
        final Function function = new Function(FUNC_GETROOTTOP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, CurvePoint>> getUserPublicKeys(
            String eoa) {
        final Function function = new Function(FUNC_GETUSERPUBLICKEYS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, eoa)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<CurvePoint>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, CurvePoint>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, CurvePoint>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, CurvePoint> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, CurvePoint>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (CurvePoint) results.get(2));
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getZkTransferFee() {
        final Function function = new Function(FUNC_GETZKTRANSFERFEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getZkTransferFeeAddress() {
        final Function function = new Function(FUNC_GETZKTRANSFERFEEADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String initialOwner, BigInteger depth,
            List<BigInteger> vk, BigInteger price, String toReceiveFee) {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, initialOwner), 
                new org.web3j.abi.datatypes.generated.Uint256(depth), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(vk, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(price), 
                new org.web3j.abi.datatypes.Address(160, toReceiveFee)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isNullified(BigInteger nf) {
        final Function function = new Function(FUNC_ISNULLIFIED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(nf)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> nfList(BigInteger param0) {
        final Function function = new Function(FUNC_NFLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<byte[]> onERC1155BatchReceived(String operator, String from,
            List<BigInteger> ids, List<BigInteger> values, byte[] data) {
        final Function function = new Function(FUNC_ONERC1155BATCHRECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(ids, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(values, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> onERC1155Received(String operator, String from, BigInteger id,
            BigInteger value, byte[] data) {
        final Function function = new Function(FUNC_ONERC1155RECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.generated.Uint256(value), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> onERC721Received(String operator, String from,
            BigInteger tokenId, byte[] data) {
        final Function function = new Function(FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> pendingOwner() {
        final Function function = new Function(FUNC_PENDINGOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> proxiableUUID() {
        final Function function = new Function(FUNC_PROXIABLEUUID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAuditor(List<BigInteger> apk) {
        final Function function = new Function(
                FUNC_REGISTERAUDITOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(apk, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerUser(BigInteger addr, BigInteger pkOwn,
            List<BigInteger> pkEnc) {
        final Function function = new Function(
                FUNC_REGISTERUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(addr), 
                new org.web3j.abi.datatypes.generated.Uint256(pkOwn), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(pkEnc, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> rtList(BigInteger param0) {
        final Function function = new Function(FUNC_RTLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setZkTransferFee(BigInteger price) {
        final Function function = new Function(
                FUNC_SETZKTRANSFERFEE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setZkWalletFeeReceiver(String addr) {
        final Function function = new Function(
                FUNC_SETZKWALLETFEERECEIVER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> tokenReceived(String _from, BigInteger _value,
            byte[] _data) {
        final Function function = new Function(
                FUNC_TOKENRECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradeToAndCall(String newImplementation,
            byte[] data, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_UPGRADETOANDCALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newImplementation), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> version() {
        final Function function = new Function(FUNC_VERSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> zkTransfer1155(List<BigInteger> proof,
            List<BigInteger> inputs, String toEoA, BigInteger enaIndex, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ZKTRANSFER1155, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(proof, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(inputs, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.Address(160, toEoA), 
                new org.web3j.abi.datatypes.generated.Uint256(enaIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> zkTransfer20(List<BigInteger> proof,
            List<BigInteger> inputs, String toEoA, BigInteger enaIndex, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ZKTRANSFER20, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(proof, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(inputs, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.Address(160, toEoA), 
                new org.web3j.abi.datatypes.generated.Uint256(enaIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> zkTransfer721(List<BigInteger> proof,
            List<BigInteger> inputs, String toEoA, BigInteger enaIndex, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ZKTRANSFER721, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(proof, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(inputs, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.Address(160, toEoA), 
                new org.web3j.abi.datatypes.generated.Uint256(enaIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    @Deprecated
    public static Groth16AltBN128Mixer load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Groth16AltBN128Mixer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Groth16AltBN128Mixer load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Groth16AltBN128Mixer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Groth16AltBN128Mixer load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Groth16AltBN128Mixer(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Groth16AltBN128Mixer load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Groth16AltBN128Mixer(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class CurvePoint extends StaticStruct {
        public BigInteger x;

        public BigInteger y;

        public CurvePoint(BigInteger x, BigInteger y) {
            super(new org.web3j.abi.datatypes.generated.Uint256(x), 
                    new org.web3j.abi.datatypes.generated.Uint256(y));
            this.x = x;
            this.y = y;
        }

        public CurvePoint(Uint256 x, Uint256 y) {
            super(x, y);
            this.x = x.getValue();
            this.y = y.getValue();
        }
    }

    public static class ENA extends StaticStruct {
        public BigInteger sR;

        public List<BigInteger> sCT;

        public ENA(BigInteger sR, List<BigInteger> sCT) {
            super(new org.web3j.abi.datatypes.generated.Uint256(sR), 
                    new org.web3j.abi.datatypes.generated.StaticArray3<org.web3j.abi.datatypes.generated.Uint256>(
                            org.web3j.abi.datatypes.generated.Uint256.class,
                            org.web3j.abi.Utils.typeMap(sCT, org.web3j.abi.datatypes.generated.Uint256.class)));
            this.sR = sR;
            this.sCT = sCT;
        }

        public ENA(Uint256 sR, @Parameterized(type = Uint256.class) StaticArray3<Uint256> sCT) {
            super(sR, sCT);
            this.sR = sR.getValue();
            this.sCT = sCT.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
        }
    }

    public static class InitializedEventResponse extends BaseEventResponse {
        public BigInteger version;
    }

    public static class LogDebugEventResponse extends BaseEventResponse {
        public byte[] message;
    }

    public static class LogUserRegisterEventResponse extends BaseEventResponse {
        public BigInteger addr;

        public BigInteger pkOwn;

        public CurvePoint pkEnc;
    }

    public static class LogZkTransferEventResponse extends BaseEventResponse {
        public BigInteger nullifier;

        public BigInteger com;

        public List<BigInteger> ct;

        public BigInteger numLeaves;

        public List<BigInteger> ena;
    }

    public static class OwnershipTransferStartedEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class UpgradedEventResponse extends BaseEventResponse {
        public String implementation;
    }
}
