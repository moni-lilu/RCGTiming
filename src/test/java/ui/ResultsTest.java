package ui;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.google.gson.Gson;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.*;

import static io.restassured.RestAssured.given;

public class ResultsTest {

    FormatPage formatPage = new FormatPage();
    static Controller controller = new Controller();

    @BeforeClass
    public static void setUp() throws InterruptedException {
        RestAssured.baseURI = controller.getUrl();
        Selenide.open(controller.getUrl());
        controller.userSignIn(controller.getAdminEmail(), controller.getAdminPassword());
        MainPage mainPage = new MainPage();
        mainPage.getDownloadButtonInMainPage().shouldHave(Condition.visible);
    }

    @Before
    public void openFormatPage() {
        Selenide.open("https://stage.rcgtiming.com/Timing/Competition?id=385#format");
    }

    @AfterClass
    public static void adminExit() throws InterruptedException {
        controller.adminLogOut();
    }

    @Test
    @DisplayName("Get status code")
    @Description("Should return status code 200")
    public void getStatusCode() {
        given()
                .header("X-Auth-Token", controller.getAuthToken())
                .get("/API/CompetitionRaceResults?id=1835")
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Best lap position")
    @Description("Positions should be allocated by the best lap")
    public void positionsShouldBeAllocatedByTheBestLap() throws InterruptedException {
        selectTab("Seeding");
        chooseRankingRule("Single best lap");
        Boolean rangeIsCorrect = checkRangeIsCorrect("Single best lap");
        Assert.assertEquals(true, rangeIsCorrect);
    }

    @Test
    @DisplayName("Best three laps position")
    @Description("Positions should be allocated by three best laps")
    public void positionsShouldBeAllocatedByTheBestThreeLaps() throws InterruptedException {
        selectTab("Seeding");
        chooseRankingRule("Best three-lap sequence");
        Boolean rangeIsCorrect = checkRangeIsCorrect("Best three-lap sequence");
        Assert.assertEquals(true, rangeIsCorrect);
    }

    @Test
    @DisplayName("By the number of laps for the personal time position")
    @Description("Positions should be allocated by the number of laps for the personal time")
    public void positionsShouldBeAllocatedByTheNumberOfLapsForThePersonalTime() throws InterruptedException {
        selectTab("Seeding");
        chooseRankingRule("Number of laps for the personal time");
        Boolean rangeIsCorrect = checkRangeIsCorrect("Number of laps for the personal time");
        Assert.assertEquals(true, rangeIsCorrect);
    }

    @Test
    @DisplayName("By the number of laps for the overall time position")
    @Description("Positions should be allocated by the number of laps for the overall time")
    public void positionsShouldBeAllocatedByTheNumberOfLapsForTheOverallTime() throws InterruptedException {
        selectTab("Seeding");
        chooseRankingRule("Number of laps for the overall time");
        Boolean rangeIsCorrect = checkRangeIsCorrect("Number of laps for the overall time");
        Assert.assertEquals(true, rangeIsCorrect);
    }

    public void chooseRankingRule(String rankingRule) {
        switch (rankingRule) {
            case "Single best lap":
                formatPage.getSingleBestLapRadioButton().click();
                break;
            case "Best three-lap sequence":
                formatPage.getBestThreeLapRadioButton().click();
                break;
            case "Number of laps for the personal time":
                formatPage.getNumberOfLapsForThePersonalTimeRadioButton().click();
                break;
            case "Number of laps for the overall time":
                formatPage.getNumberOfLapsForTheOverallTimeRadioButton().click();
                break;
        }
        formatPage.getButtonSaveInFormatTab().click();
    }

    public void selectTab(String raceType) throws InterruptedException {
        switch (raceType) {
            case "Seeding":
                formatPage.getSeedingTab().click();
            case "Qualification":
                break;
            case "Main":
                break;
        }

        Thread.sleep(1000);
    }

    public Data getJsonData() {
        Data data = given()
                .header("X-Auth-Token", controller.getAuthToken())
                .header("Accept", "application/json")
                .get("/API/CompetitionRaceResults?id=1835")
                .body().as(Data.class);
        /*Gson gson = new Gson();
        String json = gson.toJson(data);*/
        return data;
    }

    public Boolean checkRangeIsCorrect(String rankingRule) {
        Data raceData = getJsonData();
        Boolean rangeIsCorrect = null;
        int n = raceData.getData().size();
        Double[] time = new Double[n];
        Integer[] position = new Integer[n];
        Integer[] lapsCount = new Integer[n];
        Boolean totalTimeIsCorrect = null;
        String checkRangeIsCorrect = "";
        float totalTimeControl = 0;

        for (int i = 0; i < n; i++) {
            Gson gson = new Gson();
            switch (rankingRule) {
                case "Single best lap":
                    time[i] = Double.valueOf(gson.toJson(raceData.getData().get(i).getBestLapTime()));
                    checkRangeIsCorrect = "case 1";
                    break;
                case "Best three-lap sequence":
                    time[i] = Double.valueOf(gson.toJson(raceData.getData().get(i).getBestThreeTime()));
                    checkRangeIsCorrect = "case 1";
                    break;
                case "Number of laps for the personal time":
                    time[i] = Double.valueOf(gson.toJson(raceData.getData().get(i).getTotalTime()));
                    lapsCount[i] = Integer.valueOf(gson.toJson(raceData.getData().get(i).getLapsCount()));
                    checkRangeIsCorrect = "case 2";
                    for (int j = 1; j <= lapsCount[i]; j++) {
                        totalTimeControl = totalTimeControl + Float.valueOf(gson.toJson(raceData
                                .getData()
                                .get(i)
                                .getLaps()
                                .get(j)
                                .getLaptime()));
                    }
                    totalTimeIsCorrect = roundNumber(totalTimeControl) == roundNumber(time[i]);
                    totalTimeControl = 0;
                    break;
                case "Number of laps for the overall time":
                    time[i] = Double.valueOf(gson.toJson(raceData.getData().get(i).getTotalTime()));
                    lapsCount[i] = Integer.valueOf(gson.toJson(raceData.getData().get(i).getLapsCount()));
                    checkRangeIsCorrect = "case 2";
                    for (int j = 0; j <= lapsCount[i]; j++) {
                        totalTimeControl = totalTimeControl + Float.valueOf(gson.toJson(raceData
                                .getData()
                                .get(i)
                                .getLaps()
                                .get(j)
                                .getLaptime()));
                    }
                    totalTimeIsCorrect = roundNumber(totalTimeControl) == roundNumber(time[i]);
                    totalTimeControl = 0;
                    break;
            }
            position[i] = Integer.valueOf(gson.toJson(raceData.getData().get(i).getPosition()));
        }
        for (int i = 0; i < n-1; i++) {
            switch (checkRangeIsCorrect) {
                case "case 1":
                    rangeIsCorrect = (time[i] < time[i + 1]) && (position[i] < position[i + 1]);
                    break;
                case "case 2":
                    rangeIsCorrect = (time[i] > time[i + 1]) && (position[i] < position[i + 1]) && (lapsCount[i] >= lapsCount[i+1]) && totalTimeIsCorrect;
                    break;
            }
        }
        return rangeIsCorrect;
    }
    public double roundNumber(double number) {
        double scale = Math.pow(10, 3);
        double result = Math.ceil(number * scale);
        return result;
    }
}
