package main.service.mapping;

import java.util.List;
import java.util.stream.Collectors;
import main.data.models.TableConstraint;
import main.service.models.GroupedColumn;
import main.service.models.GroupedConstraint;
import main.service.models.GroupedTable;

public class ConstraintMapping {
	public ConstraintMapping() {
		throw new AssertionError();
	}
	
	public static List<GroupedConstraint> mapConstraints(
			List<GroupedTable> tables, 
			List<TableConstraint> constraints) {
		List<GroupedConstraint> list = constraints
				.stream()
				.map(constraint -> mapConstraint(tables, constraint))
				.collect(Collectors.toList());		
		return list;
	}
	
	private static GroupedConstraint mapConstraint(
			List<GroupedTable> tables, 
			TableConstraint constraint) {
		
		GroupedTable table = tables
				.stream()
				.filter(t -> t.getName().equals(constraint.getTable()))
				.findFirst()
				.orElse(null);
		GroupedColumn column = table.getColumns()
				.stream()
				.filter(c -> c.getName().equals(constraint.getColumn()))
				.findFirst()
				.orElse(null);
		
		GroupedTable referencedTable = tables
				.stream()
				.filter(t -> t.getName().equals(constraint.getReferencedTable()))
				.findFirst()
				.orElse(null);
		GroupedColumn referencedColumn = referencedTable.getColumns()
				.stream()
				.filter(c -> c.getName().equals(constraint.getReferencedColumn()))
				.findFirst()
				.orElse(null);
		
		GroupedConstraint groupedConstraint = new GroupedConstraint();
		groupedConstraint.setColumn(column);	
		groupedConstraint.setReferencedColumn(referencedColumn);
		
		return groupedConstraint;
	}
}
