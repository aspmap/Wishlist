package run.itlife.service;

import run.itlife.dto.BugsDto;
import run.itlife.entity.Bugs;

import java.util.List;

public interface BugsService {
    void createBugReport(BugsDto bugsDto);
    List<Bugs> findAllBugs();
    void createBugReportFromKafka(BugsDto bugsDto);
}
