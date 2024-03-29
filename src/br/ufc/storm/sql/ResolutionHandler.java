package br.ufc.storm.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import br.ufc.storm.control.Resolution;
import br.ufc.storm.exception.DBHandlerException;
import br.ufc.storm.export.FormalFormat;
import br.ufc.storm.jaxb.CalculatedArgumentType;
import br.ufc.storm.jaxb.CalculatedParameterType;
import br.ufc.storm.jaxb.ContextArgumentType;
import br.ufc.storm.jaxb.ContextArgumentValueType;
import br.ufc.storm.jaxb.ContextContract;
import br.ufc.storm.jaxb.ContextParameterType;
import br.ufc.storm.jaxb.PlatformProfileType;
import br.ufc.storm.model.ArgumentTable;
import br.ufc.storm.model.ResolutionNode;

public class ResolutionHandler extends DBHandler {
	private static final String SELECT_CONTEXT_CONTRACT_BY_AC_ID = "select cc_id from context_contract where ac_id = ?;";
	private static final String SELECT_PLATFORM_BY_AC_ID = "select * from context_contract A, platform_owner B where A.cc_id = B.platform_cc_id and A.ac_id = ? and kind_id = 2;";
	private final static String SELECT_ABSTRACT_COMPONENT_BY_SUPERTYPE_ID = "select ac_name, ac_id, supertype_id, enabled from abstract_component WHERE supertype_id = ?;";

	public static void main(String[] args) {
		try {
			Resolution.main(args);
		} catch (DBHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method generates a list os all components candidates to a contract but only in the highest level, it will be refined.
	 * @param supertypeID
	 * @param requiredID
	 * @param resolutionTree 
	 * @return
	 * @throws DBHandlerException 
	 */

	public static List<ContextContract> generateCandidates(int supertypeID, int requiredID) throws DBHandlerException{
		PreparedStatement prepared;
		ResultSet resultSet;
		List<ContextContract> list = new ArrayList<ContextContract>();
		int history;
		int supertype = 0;
		int aux = requiredID;
		try {
			Connection con = getConnection();
			do{
				prepared = con.prepareStatement(SELECT_CONTEXT_CONTRACT_BY_AC_ID);
				prepared.setInt(1, aux);
				resultSet = prepared.executeQuery();
				while(resultSet.next()){
					Integer cc_id = resultSet.getInt("cc_id");
					ContextContract cc = ContextContractHandler.getContextContract(cc_id);
					cc.setPlatform(new PlatformProfileType());
					cc.getPlatform().setPlatformContract(PlatformHandler.getPlatform(cc_id));
					list.add(cc);
				}
				supertype = ResolutionNode.resolutionTree.findNode(list.get(list.size()-1).getAbstractComponent().getIdAc()).getSupertype().getAc_id();
				history = aux;
				aux = supertype;
			}while(resultSet!= null && history != supertypeID );
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBHandlerException("A sql error occurred: ", e);
		}
	}

	/**
	 * This method generates a set of platforms stored in core
	 * @param supertypeID
	 * @param resolutionTree
	 * @return
	 * @throws DBHandlerException 
	 */

	public static List<ContextContract> generateCompliantPlatformCandidates(int requiredID) throws DBHandlerException{
		try {	
			List <ResolutionNode> subtype = new ArrayList<ResolutionNode>();
			List<ContextContract> list = new ArrayList<ContextContract>();
			Connection con = getConnection();
			PreparedStatement prepared = con.prepareStatement(SELECT_PLATFORM_BY_AC_ID);
			prepared.setInt(1, requiredID); 
			ResultSet resultSet = prepared.executeQuery(); 
			while(resultSet.next()) { 
				Integer cc_id = resultSet.getInt("platform_cc_id");
				try {
					ContextContract candidate = ContextContractHandler.getContextContract(cc_id);



					//aaaaaaaaaaaaaaaaa					
					ResolutionHandler.mergeFreeParameters(candidate);







					list.add(candidate);
				} catch (DBHandlerException e) {
					throw new RuntimeException("Platform can not be catched with ac_id "+requiredID,e);
				} 
			} 
			subtype = ResolutionNode.resolutionTree.findNode(requiredID).getSubtype();
			for(ResolutionNode rn:subtype){
				list.addAll(generateCompliantPlatformCandidates(rn.getAc_id()));
			}
			return list;
		} catch (SQLException e) {
			throw new DBHandlerException("An error occurred while trying to generate a compliant platform", e);
		}

	}

	private static void mergeFreeParameters(ContextContract candidate) {
		Hashtable <Integer , ContextArgumentType> tableOfArguments = new Hashtable<Integer , ContextArgumentType>();
		for(ContextArgumentType arg:candidate.getContextArguments()){
			tableOfArguments.put(arg.getCpId(), arg);
		}
		if(candidate.getAbstractComponent() != null ){
			for(ContextParameterType cpt:candidate.getAbstractComponent().getContextParameter()){
				if(tableOfArguments.get(cpt.getCpId())==null){
					ContextArgumentType cat = new ContextArgumentType();
					cat.setCpId(cpt.getCpId());
					cat.setCcId(candidate.getCcId());
					cat.setKind(cpt.getKind());

					if(cpt.getKind()==1){
						try {
							cat.setContextContract(ContextContractHandler.getContextContract(cpt.getBound().getCcId()));
						} catch (DBHandlerException e) {
							e.printStackTrace();
						}
					}else{
						if(cpt.getKind() >= 3){
							cat.setValue(new ContextArgumentValueType());
							cat.getValue().setValue(cpt.getBoundValue());
						}
					}
					candidate.getContextArguments().add(cat);
				}
			}
		}



	}

	/**
	 * This method generates a resolution tree from an specified node 
	 * All abstract components must be a subtype of root or another subtype off root, because if not, this component won't be catched.
	 * @return
	 * @throws DBHandlerException 
	 */
	public static ResolutionNode generateResolutionTree(Integer i, ResolutionNode tree, List<ContextParameterType> cps, List<CalculatedParameterType> qps, List<CalculatedParameterType> cops, List<CalculatedParameterType> rps, String parentPath) throws DBHandlerException{
		try {
			Connection con = DBHandler.getConnection();
			PreparedStatement prepared = con.prepareStatement(SELECT_ABSTRACT_COMPONENT_BY_SUPERTYPE_ID); 
			prepared.setInt(1, i);
			ResultSet resultSet = prepared.executeQuery(); 
			while (resultSet.next()) {
				if(resultSet.getBoolean("enabled") == false){
					return null;
				}
				ResolutionNode node = new ResolutionNode();
				node.setName(resultSet.getString("ac_name"));
				node.setPath(parentPath);
				node.setAc_id(resultSet.getInt("ac_id"));
				node.setCps(ContextParameterHandler.getAllContextParameterFromAbstractComponent(node.getAc_id()));
				node.getCps().addAll(cps);

				node.setQps(CalculatedArgumentHandler.getCalculatedParameters(node.getAc_id(), ContextParameterHandler.QUALITY));
				if(qps.size() > 0){
					node.getQps().addAll(qps);
				}

				node.setCops(CalculatedArgumentHandler.getCalculatedParameters(node.getAc_id(), ContextParameterHandler.COST));
				if(cops.size() > 0){
					node.getCops().addAll(cops);
				}

				node.setRps(CalculatedArgumentHandler.getCalculatedParameters(node.getAc_id(), ContextParameterHandler.RANKING));
				if(rps.size() > 0){
					node.getRps().addAll(rps);
				}
				node.setSupertype(tree);
				tree.addSubtype(node);
				ResolutionNode.addTable(node.getAc_id(), node);
				generateResolutionTree(node.getAc_id(), node, node.getCps(), node.getQps(), node.getCops(), node.getRps(), node.getPath()+"."+node.getName());

			} 
			return tree;
		} catch (SQLException e) { 
			throw new DBHandlerException("A sql error occurred: ", e);
		} 	

	}



}
