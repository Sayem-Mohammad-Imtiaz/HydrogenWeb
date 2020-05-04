; ModuleID = 'Prog.c'
source_filename = "Prog.c"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

@.str = private unnamed_addr constant [6 x i8] c"%f %f\00", align 1
@.str.1 = private unnamed_addr constant [56 x i8] c"The area of (%.4f,%.4f), (%.4f,0) and (0,%.4f) is %.4f.\00", align 1

; Function Attrs: noinline nounwind sspstrong uwtable
define dso_local i32 @main() #0 !dbg !9 {
  %1 = alloca i32, align 4
  %2 = alloca float, align 4
  %3 = alloca float, align 4
  %4 = alloca float, align 4
  store i32 0, i32* %1, align 4
  call void @llvm.dbg.declare(metadata float* %2, metadata !13, metadata !DIExpression()), !dbg !15
  call void @llvm.dbg.declare(metadata float* %3, metadata !16, metadata !DIExpression()), !dbg !17
  call void @llvm.dbg.declare(metadata float* %4, metadata !18, metadata !DIExpression()), !dbg !19
  %5 = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([6 x i8], [6 x i8]* @.str, i32 0, i32 0), float* %2, float* %3), !dbg !20
  %6 = load float, float* %2, align 4, !dbg !21
  %7 = fpext float %6 to double, !dbg !21
  %8 = fmul double 5.000000e-01, %7, !dbg !22
  %9 = load float, float* %3, align 4, !dbg !23
  %10 = fpext float %9 to double, !dbg !23
  %11 = fmul double %8, %10, !dbg !24
  %12 = fptrunc double %11 to float, !dbg !25
  store float %12, float* %4, align 4, !dbg !26
  %13 = load float, float* %4, align 4, !dbg !27
  %14 = fcmp ogt float %13, 0.000000e+00, !dbg !29
  br i1 %14, label %15, label %27, !dbg !30

; <label>:15:                                     ; preds = %0
  %16 = load float, float* %2, align 4, !dbg !31
  %17 = fpext float %16 to double, !dbg !31
  %18 = load float, float* %3, align 4, !dbg !32
  %19 = fpext float %18 to double, !dbg !32
  %20 = load float, float* %2, align 4, !dbg !33
  %21 = fpext float %20 to double, !dbg !33
  %22 = load float, float* %3, align 4, !dbg !34
  %23 = fpext float %22 to double, !dbg !34
  %24 = load float, float* %4, align 4, !dbg !35
  %25 = fpext float %24 to double, !dbg !35
  %26 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([56 x i8], [56 x i8]* @.str.1, i32 0, i32 0), double %17, double %19, double %21, double %23, double %25), !dbg !36
  br label %40, !dbg !36

; <label>:27:                                     ; preds = %0
  %28 = load float, float* %2, align 4, !dbg !37
  %29 = fpext float %28 to double, !dbg !37
  %30 = load float, float* %3, align 4, !dbg !38
  %31 = fpext float %30 to double, !dbg !38
  %32 = load float, float* %2, align 4, !dbg !39
  %33 = fpext float %32 to double, !dbg !39
  %34 = load float, float* %3, align 4, !dbg !40
  %35 = fpext float %34 to double, !dbg !40
  %36 = load float, float* %4, align 4, !dbg !41
  %37 = fsub float -0.000000e+00, %36, !dbg !42
  %38 = fpext float %37 to double, !dbg !42
  %39 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([56 x i8], [56 x i8]* @.str.1, i32 0, i32 0), double %29, double %31, double %33, double %35, double %38), !dbg !43
  br label %40

; <label>:40:                                     ; preds = %27, %15
  ret i32 0, !dbg !44
}

; Function Attrs: nounwind readnone speculatable
declare void @llvm.dbg.declare(metadata, metadata, metadata) #1

declare i32 @__isoc99_scanf(i8*, ...) #2

declare i32 @printf(i8*, ...) #2

attributes #0 = { noinline nounwind sspstrong uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "min-legal-vector-width"="0" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nounwind readnone speculatable }
attributes #2 = { "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }

!llvm.dbg.cu = !{!0}
!llvm.module.flags = !{!3, !4, !5, !6, !7}
!llvm.ident = !{!8}

!0 = distinct !DICompileUnit(language: DW_LANG_C99, file: !1, producer: "clang version 8.0.0 (tags/RELEASE_800/final)", isOptimized: false, runtimeVersion: 0, emissionKind: FullDebug, enums: !2, nameTableKind: None)
!1 = !DIFile(filename: "Prog.c", directory: "/hydrogen/TestPrograms/Correct")
!2 = !{}
!3 = !{i32 2, !"Dwarf Version", i32 4}
!4 = !{i32 2, !"Debug Info Version", i32 3}
!5 = !{i32 1, !"wchar_size", i32 4}
!6 = !{i32 7, !"PIC Level", i32 2}
!7 = !{i32 7, !"PIE Level", i32 2}
!8 = !{!"clang version 8.0.0 (tags/RELEASE_800/final)"}
!9 = distinct !DISubprogram(name: "main", scope: !1, file: !1, line: 2, type: !10, scopeLine: 2, spFlags: DISPFlagDefinition, unit: !0, retainedNodes: !2)
!10 = !DISubroutineType(types: !11)
!11 = !{!12}
!12 = !DIBasicType(name: "int", size: 32, encoding: DW_ATE_signed)
!13 = !DILocalVariable(name: "a", scope: !9, file: !1, line: 3, type: !14)
!14 = !DIBasicType(name: "float", size: 32, encoding: DW_ATE_float)
!15 = !DILocation(line: 3, column: 9, scope: !9)
!16 = !DILocalVariable(name: "b", scope: !9, file: !1, line: 3, type: !14)
!17 = !DILocation(line: 3, column: 12, scope: !9)
!18 = !DILocalVariable(name: "area", scope: !9, file: !1, line: 3, type: !14)
!19 = !DILocation(line: 3, column: 15, scope: !9)
!20 = !DILocation(line: 4, column: 3, scope: !9)
!21 = !DILocation(line: 5, column: 15, scope: !9)
!22 = !DILocation(line: 5, column: 13, scope: !9)
!23 = !DILocation(line: 5, column: 19, scope: !9)
!24 = !DILocation(line: 5, column: 17, scope: !9)
!25 = !DILocation(line: 5, column: 10, scope: !9)
!26 = !DILocation(line: 5, column: 8, scope: !9)
!27 = !DILocation(line: 6, column: 7, scope: !28)
!28 = distinct !DILexicalBlock(scope: !9, file: !1, line: 6, column: 7)
!29 = !DILocation(line: 6, column: 12, scope: !28)
!30 = !DILocation(line: 6, column: 7, scope: !9)
!31 = !DILocation(line: 7, column: 71, scope: !28)
!32 = !DILocation(line: 7, column: 74, scope: !28)
!33 = !DILocation(line: 7, column: 77, scope: !28)
!34 = !DILocation(line: 7, column: 80, scope: !28)
!35 = !DILocation(line: 7, column: 83, scope: !28)
!36 = !DILocation(line: 7, column: 5, scope: !28)
!37 = !DILocation(line: 9, column: 71, scope: !28)
!38 = !DILocation(line: 9, column: 74, scope: !28)
!39 = !DILocation(line: 9, column: 77, scope: !28)
!40 = !DILocation(line: 9, column: 80, scope: !28)
!41 = !DILocation(line: 9, column: 84, scope: !28)
!42 = !DILocation(line: 9, column: 83, scope: !28)
!43 = !DILocation(line: 9, column: 5, scope: !28)
!44 = !DILocation(line: 10, column: 3, scope: !9)
